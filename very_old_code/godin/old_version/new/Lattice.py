#!/usr/bin/python
#coding=utf-8
# Filename : Lattice.py 

class Lattice:

	def __init__(self, BR=None):
		'''initialize the Lattice'''
		self.obj_dic = {}						#存放对象及其属性
		self.conceptSet = []					#概念的集合
		self.supConcept = Concept()
		self.conceptSet.append(self.supConcept)
		self.botConcept = Concept()
		self.conceptSet.append(self.botConcept)
		self.edgeSet = []						#边的集合
		if BR:
			# get object从二元关系得到obj_dic 也可提出到类外，然后BR可以省略，直接dic
			for obj in range(0, len(BR.object_list)):
				tmpSet = set()
				for att in range(0, len(BR.attribute_list)):
					if BR.value_list[obj][att] == 1:
						tmpSet.add(BR.attribute_list[att])
				self.obj_dic[BR.object_list[obj]] = tmpSet
			print self.obj_dic

	def addConcept(self, newconcept):
		'''add a new concept to conceptSet'''
		self.conceptSet.append(newconcept)
		#print 'the conceptSet num is : %d' % len(self.conceptSet)

	def addEdge(self, fromID, toID):
		'''add a new edge to edgeSet'''
		newedge = Edge(fromID, toID)
		self.edgeSet.append(newedge)
		#print 'the edgeSet num is : %d' % len(self.edgeSet)

	def deleteEdge(self, fromID, toID):
		'''delete one edge from edgeSet'''
		for oneedge in self.edgeSet:
			if oneedge.getEdge() == (fromID, toID):
				print 'remove edge', oneedge
				self.edgeSet.remove(oneedge)
				return True # have this edge and remove it
		return False # havn't this edge


	def getChild(self, pConcept):
		''' get oneConcept's Child List'''
		pConceptID = pConcept.getConceptID()
		childList = []
		for edge in self.edgeSet:
			if edge.getPID() == pConceptID:
				for cpt in self.conceptSet:
					if cpt.getConceptID() == edge.getCID():
						childList.append(cpt)
						break
		return childList

	def getParent(self, cConcept):# 现在不要这个函数了
		''' get oneConcept's Parent List'''
		cConceptID = cConcept.getConceptID()
		parentList = []
		for edge in self.edgeSet:
			if edge.getCID() == cConceptID:
				for cpt in self.conceptSet:
					if cpt.getConceptID() == edge.getPID():
						parentList.append(cpt)
						break
		#[cpt for cpt in self.conceptSet if cpt.getConceptID() == edge.getPID()]
		return parentList

	def doGodinAlgorithm(self):
		'''Godin Algorithm'''
		for one in self.obj_dic:
			self.addOneObject(one)

	def addOneObject(self, oneObject):
		'''Add a new object'''
		xSet = set([oneObject])						# the set of oneobject's extent
		fxSet = set(self.obj_dic[oneObject])		# the set of oneobject's intent
		if self.supConcept.isNone():				# supConcept is None or not
			self.supConcept.addExtent(xSet)			# replace supConcept use object
			self.supConcept.addIntent(fxSet)
			self.botConcept.addExtent(xSet)			# add this object to botConcept
			return True
		if not fxSet <= self.supConcept.getIntent():			# fxSet contains supConcept's intent or not
			if not self.supConcept.getExtent():					# supConcept's extent is none
				self.supConcept.addIntent(fxSet)				# add this object's intent to supConcept's
			else:
				H = Concept(set(), self.supConcept.getIntent().union(fxSet))		# creat new concept: {},{supConcept'intent
				self.addConcept(H)																#									+ fxSet
				self.addEdge(self.supConcept.getConceptID(), H.getConceptID())		# creat new edge from supConcept to this new
				self.supConcept = H																# change supConcept with this new concept
			#end if
		#end if
		C = {}								# 把原来概念格中概念根据内涵的数量的不同进行分类C[i]<-{H:||X'(H)||=i};
		for onecpt in self.conceptSet:		# consider all concept, get different length of intent
			i = len(onecpt.getIntent())
			if C.has_key(i):
				#C[i].extent([onecpt])
				#C[i] += onecpt
				C[i].append(onecpt)
			else:
				C[i] = [onecpt]
		CAnother = {}							# 初始化所有更新格节点和所有新生成的格节点的集合为空
		for i in C.keys():						# 从intent的数目由小到大考虑各个概念
			for H in C[i]:
				if H.getIntent() <= fxSet:		# 这个概念的intent包含于fxSet，需要修改这个概念
					H.addExtent(xSet)			# 加入对象xSet，相应的CAnother也得加入
					if CAnother.has_key(i):
						CAnother[i].append(H)
					else:
						CAnother[i] = [H]
					if H.getIntent() == fxSet:	#若这个概念的intent＝fxSet，exit algorithm
						return True
				else:												# 这个概念的intent不包含于fxSet，old concept
					intSet = H.getIntent().intersection(fxSet)		# 得到这个概念的intent和fxSet的交集
					len_intSet = len(intSet)
					flaglist = []									# 存在标志，要表达出：在更新概念CAnother且intent的势为交集势的概念中
					if CAnother.has_key(len_intSet):				# 不存在一更新概念的intent＝这个交集
						flaglist = [H1 for H1 in CAnother[len_intSet] if H1.getIntent() == intSet]
					#flags = True
					#for H1 in CAnother[len(intSet)]:# 有个综合表达式的
					#	if H1.getIntent() == intSet:
					#		flags = False
					#		break
					#if flags:
					if not flaglist:											# not exisit, show that H is a generator
						Hn = Concept(H.getExtent().union(xSet), intSet)			# new concept:extent+xSet, intSet
						self.addConcept(Hn)
						if CAnother.has_key(len_intSet):						# add this new concept to CAnother
							CAnother[len_intSet].append(Hn)
						else:
							CAnother[len_intSet] = [Hn]
						self.addEdge(Hn.getConceptID(), H.getConceptID())		# add new edge: from this new concept to current
						jlist = [ j for j in range(0, len_intSet) if CAnother.has_key(j)]	# modify edges
						for j in jlist:														# 找更新概念中
							for Ha in CAnother[j]:
								if Ha.getIntent() <= intSet:					# Ha is a potential parent of Hn 
									parent = True
									child_of_Ha = self.getChild(Ha)				# Ha's Children
									for Hd in child_of_Ha:
										if Hd.getIntent() <= intSet:			# 只要有一个更新概念的孩子的intent包含于这个交集，
											parent = False						# 就不是父节点
											break
									# end for
									if parent:												# 是父节点
										if H in child_of_Ha:								# 当前的概念是不是他的孩子，是的话，删除边
											self.deleteEdge(Ha.getConceptID(), H.getConceptID())	# eliminate edge Ha->H
										self.addEdge(Ha.getConceptID(), Hn.getConceptID())			# 增加父节点到这个新概念的边
									# end if
								# end if
							# end for
						# end for
						if intSet == fxSet:						# 哪种情况？？
							return True
						# end if
					# end if
				# end if
			# end for
		# end for
	# end Add

	def __str__(self):
		selfstring = 'Concept------>\n'
		for cpt in self.conceptSet:
			selfstring += '%s\n' % cpt
		selfstring += 'Edge is------>\n'
		for e in self.edgeSet:
			selfstring += '%s\n' % e
		return selfstring

class Concept:
	conceptNUM = 0

	def __init__(self, extent=set(), intent=set()):
		Concept.conceptNUM += 1
		self.conceptID = Concept.conceptNUM
		self.extent = extent
		self.intent = intent

	def isNone(self):
		''' None None Concept'''
		if self.extent or self.intent:
			return False
		return True

	def getExtent(self):
		return self.extent

	def getIntent(self):
		return self.intent

	def addIntent(self, intentSet):
		self.intent = self.intent.union(intentSet)

	def addExtent(self, extentSet):
		self.extent = self.extent.union(extentSet)

	def getConceptID(self):
		return self.conceptID

	def __str__(self):
		return 'Concept %d: %s----%s' % (self.conceptID, self.extent, self.intent)
	
class Edge:
	def __init__(self, pConceptID=0, cConceptID=0):
		self.parentConceptID = pConceptID
		self.childConceptID = cConceptID

	def getEdge(self):
		return (self.parentConceptID, self.childConceptID)

	def getPID(self):
		return self.parentConceptID

	def getCID(self):
		return self.childConceptID

	def __str__(self):
		return 'Edge: %d--->%d' % (self.parentConceptID, self.childConceptID)
