import sys 
import random 



def binary_search(arr,size,key): 
	low = 0 
	high = size - 1 
	mid = (low+high) / 2 
	while low <= high: 
		flag=0 
		if key == arr[mid]: 
		    print 
		    print key,"found at position", mid +1 
		    flag = 1 
		    break 
		    
		elif key > arr[mid]: 
		    low = mid + 1 
		    mid = (low + high) / 2 
		    
		else: 
		    high = mid - 1 
		    mid = (low + high) / 2 
		    
	 
	if flag==0: 
		print key, 'Not Found!!' 
	 
arr = [] 
size=input("Enter the number of elements") 
for i in range(0, size): 
    arr.append(random.randint(0,9999)) 

print 'Unsorted array' 
print arr 

arr.sort() 
print"\nSorted array is" 
print arr 
ans=1 
while ans == 1: 
	key = input("\nEnter the element to be searched: ") 

	if key < arr[0] or key > arr[size - 1]: 
	    print "Entered key is out of bounds." 
	    sys.exit() 
	binary_search(arr, size, key) 
	ans=input("Do u wish to continue?(1/0)") 

