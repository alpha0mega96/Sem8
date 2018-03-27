from xml.dom.minidom import parse 
import xml.dom.minidom 

print "\n*****8-Queen Problem Solutions*****\n" 

class Queen : 

	def __init__(self) : 
		self.q=[0 for x in range (8)] 

	def printem(self) : 

		i = 0 
	 
		while (i < 8) : 

			print "\n\t", 

			j = self.q[i] 
			k = 8 - j 

			while (j > 0) : 
				print "-\t", 
				j=j-1 


        		print "Q\t", 

			while (k > 1) : 
				print "-\t", 
				k=k-1 

	 
			i=i+1 

		print "\n\n" 


	def dist(self, x, y) : 
		if (x > y) : 
		        return (x - y) 
		else : 
			return (y - x) 


	def match(self, i, j) : 
		k = 0 
		while (k < i) : 
			xi = k 
			xj = self.q[k] 

			if (xi == i or xj == j) : 
				return 0 
			if (self.dist(i, xi) == self.dist(j, xj)) : 
				return 0 

			k =k+1 

		while (k < 8) : 
			self.q[k] = -1 
			k = k + 1 

		return 1 


	def process(self,pos) : 

		i = pos 
		k = 0 

		print "\nBacktracking Process : \n\n" 

		while (k < 8) : 

			if (i >= 8) : 
				print "BackTracked %dQ...." %(k + 1) , 
				k = k - 1 
				while ( (self.q[k] + 1) >= 8 ) : 
					k = k - 1 
				i = self.q[k] + 1 

			ci = i 
			i = i + 1 

			if ( self.match(k, ci) == 1) : 
				i = 0 
				self.q[k] = ci 
				k = k + 1 

		print "\n\nThe Queens : \n" 
		i = 0 
		while (i < 8 ) : 
			print "\t%d:%d" %( (i + 1), (self.q[i] + 1) ), 
			i = i + 1 

		print "\n\nThe ChessBoard : \n", 
		self.printem() 

DOMTree = xml.dom.minidom.parse("data.xml") 
collection = DOMTree.documentElement 
print "parsing \'data.xml\'..." 
if collection.hasAttribute("shelf"): 
	print "Root element : %s\n" % collection.getAttribute("shelf") 

dataset = collection.getElementsByTagName("data") 

for data in dataset :  
	if data.hasAttribute("title"): 
		print "Title : %s" %data.getAttribute("title"), 
	q = Queen() 
	q.process(int(data.getElementsByTagName('value')[0].childNodes[0].data))

