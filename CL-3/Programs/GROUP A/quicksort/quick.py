import random, time 
from multiprocessing import Process, Pipe,cpu_count 
from copy import deepcopy 


def main(): 

    create_list=[] 
    from xml.dom import minidom 
    xmldoc = minidom.parse('num.xml') 
    itemarray = xmldoc.getElementsByTagName('item') 
    l=len(itemarray) 
    print "length is:" , l 
    for i in range(0,l): 
	#	print(itemarray[i].attributes['no'].value) 
              create_list.append(int(itemarray[i].attributes['no'].value)) 

    #Parallel quicksort. 
    parallelsortlist = deepcopy(create_list) 

    start = time.time() 
    n = cpu_count() 

    pconn, cconn = Pipe() 


    p = Process(target=quicksortParallel, 
                args=(parallelsortlist, cconn, n,)) 
    p.start() 

    lyst = pconn.recv() 
    print lyst 
    p.join() 

    elapsed = time.time() - start 
    print("Parallels sort") 
    print(elapsed) 


def quicksort(lyst): 

    less = [] 
    pivotList = [] 
    more = [] 
    if len(lyst) <= 1: 
        return lyst 
    else: 
        pivot = lyst[0] 
        for i in lyst: 
            if i < pivot: 
                less.append(i) 
            elif i > pivot: 
                more.append(i) 
            else: 
                pivotList.append(i) 
        less = quicksort(less) 
        more = quicksort(more) 
        return less + pivotList + more 
        
        
def quicksortParallel(lyst, conn, procNum): 
	 
   less = [] 
   pivotList = [] 
   more = [] 

   if procNum <= 0 or len(lyst) <= 1: 
       conn.send(quicksort(lyst)) 
       conn.close() 
       return 
   else: 
       pivot = lyst[0] 
       for i in lyst: 
           if i < pivot: 
               less.append(i) 
           elif i > pivot: 
               more.append(i) 
           else: 
               pivotList.append(i) 
      	 
   pconnLeft, cconnLeft = Pipe() 

   leftProc = Process(target=quicksortParallel, 
                      args=(less, cconnLeft, procNum - 1)) 

   pconnRight, cconnRight = Pipe() 
   rightProc = Process(target=quicksortParallel, 
                      args=(more, cconnRight, procNum - 1)) 

   leftProc.start() 
   rightProc.start() 

   conn.send(pconnLeft.recv()+pivotList + pconnRight.recv()) 
   conn.close() 
 #Join subprocesses. 
   leftProc.join() 
   rightProc.join() 


if __name__ == '__main__': 
    main()

