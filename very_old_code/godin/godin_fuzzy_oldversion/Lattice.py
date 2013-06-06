#!/usr/bin/python
#coding=utf-8
# Filename : Lattice.py 

class Lattice:

	def __init__(self, obj_list=[], attr_list=[]):
		'''initialize the Lattice'''
		self.conceptSet = []					#概念的集合
		self.supConcept = Concept()
		self.addConcept(self.supConcept)
		self.botConcept = Concept()
		self.addConcept(self.botConcept)
		self.layer = {}
		
		self.object_list = obj_list
		self.attribute_list = attr_list
		
	def addConcept(self, newconcept):
		'''add a new concept to conceptSet'''
		self.conceptSet.append(newconcept)
		
	def addEdge(self, parent_cpt, child_cpt):
		'''add a new edge to edgeSet'''
		parent_cpt.child_list.append(child_cpt)
		child_cpt.parent_list.append(parent_cpt)
				
		if parent_cpt.layer_num >= child_cpt.layer_num + 1: #don't change laynum
			pass
		else:
			if parent_cpt in self.layer[parent_cpt.layer_num]: #remove old layer
				self.layer[parent_cpt.layer_num].remove(parent_cpt)
			parent_cpt.layer_num = child_cpt.layer_num + 1
			if self.layer.has_key(parent_cpt.layer_num):
				#if not parent_cpt in self.layer[parent_cpt.layer_num]:
				#	self.layer[parent_cpt.layer_num].append(parent_cpt)
				self.layer[parent_cpt.layer_num].append(parent_cpt)
			else:
				self.layer[parent_cpt.layer_num] = [parent_cpt]
			self.updateLayer(parent_cpt)
	
	def updateLayer(self, child_cpt):
		for parent_cpt in child_cpt.parent_list:
			if parent_cpt.layer_num >= child_cpt.layer_num + 1: #don't change laynum
				pass
			else:
				if parent_cpt in self.layer[parent_cpt.layer_num]: #remove old layer
					self.layer[parent_cpt.layer_num].remove(parent_cpt)
				parent_cpt.layer_num = child_cpt.layer_num + 1
				if self.layer.has_key(parent_cpt.layer_num):
					#if not parent_cpt in self.layer[parent_cpt.layer_num]:
					#	self.layer[parent_cpt.layer_num].append(parent_cpt)
					self.layer[parent_cpt.layer_num].append(parent_cpt)
				else:
					self.layer[parent_cpt.layer_num] = [parent_cpt]
				self.updateLayer(parent_cpt)
			
	def deleteEdge(self, parent_cpt, child_cpt):
		'''delete one edge from edgeSet'''
		parent_cpt.child_list.remove(child_cpt)
		child_cpt.parent_list.remove(parent_cpt)

	def SelectAndClassifyPairs(self, fxSet):
		def search(H):
			global C
			global visited_flag
			visited_flag.append(H.conceptID)
			i = len(H.intent)
			if C.has_key(i):
				C[i].append(H)
			else:
				C[i] = [H]
			for Hd in H.child_list:
				if not Hd.conceptID in visited_flag:
					search(Hd)
		
		global visited_flag	
		visited_flag = []
		visited_flag.append(self.botConcept.conceptID)
		global C
		C = {}
		C[len(self.botConcept.intent)] = [self.botConcept]

		key_list = self.layer.keys()
		key_list.sort()
		if len(self.layer[key_list[-1]]) == 1: #and self.botConcept in self.layer[key_list[-1]]
		    lay = key_list[-2]
		else:
		    lay = key_list[-1]
		for x in fxSet:
			for Px in self.layer[lay]:#Px
				if not Px.conceptID in visited_flag and x in Px.intent: #only one time
					search(Px)
		
		#for la in self.layer: #广优遍历
		#	onelayer_flag = True
		#	for onecpt in self.layer[la]:
			    #if fxSet.intersection(onecpt.intent):
				#	onelayer_flag = False
		#		for x in fxSet:
		#		    if x in onecpt.intent:
		#				onelayer_flag = False
		#				i = len(onecpt.intent)
		#				if C.has_key(i):
		#					C[i].append(onecpt)
		#				else:
		#					C[i] = [onecpt]
		#				break
					#i = len(onecpt.intent)
					#if C.has_key(i):
					#	C[i].append(onecpt)
					#else:
					#	C[i] = [onecpt]
		#	if onelayer_flag:
		#		break
		return C
			    
	def addOneObject(self, oneObject, oneattribute):
		'''Add a new object'''
		#print '-------------oneobject', oneObject, oneattribute
		xSet = set(oneObject)						# the set of oneobject's extent
		#fxSet = Bin_Set.BSet(oneattribute)					# the set of oneobject's intent
		fxSet = set(oneattribute)	
		#if not self.supConcept.extent and not self.supConcept.intent.isNone:	## supConcept is None or not
		if not self.supConcept.extent and not self.supConcept.intent:
			self.supConcept.addExtent(xSet)			# replace supConcept use object
			self.supConcept.addIntent(fxSet)
			self.botConcept.addExtent(xSet)			# add this object to botConcept
			
			##self.botConcept.layer_num = 1
			##self.layer[1] = [self.botConcept]
			##self.supConcept.layer_num = 0
			##self.layer[0] = [self.supConcept]
			##self.addEdge(self.botConcept, self.supConcept)
			return True
		#if not self.supConcept.intent.contains(fxSet):		### fxSet contains supConcept's intent or not
		if not fxSet <= self.supConcept.intent:
			if not self.supConcept.extent:			# supConcept's extent is none
				self.supConcept.addIntent(fxSet)	# add this object's intent to supConcept's
			else:
				H = Concept(set(), self.supConcept.intent.union(fxSet))# creat new concept: {},{supConcept.intent+fxSet
				self.addConcept(H)
				H.layer_num = 0
				self.layer[0] = [H]
				
				self.supConcept.layer_num = 1
				self.layer[1] = [self.supConcept]
				self.botConcept.layer_num = 2
				self.layer[2] = [self.botConcept]
				self.addEdge(self.supConcept, H)		# creat new edge from supConcept to this new
				self.addEdge(self.botConcept, self.supConcept)
				self.supConcept = H						# change supConcept with this new concept
				##self.addEdge(self.supConcept, H)##
				##self.supConcept = H##
			#end if
		#end if
		
		#C = self.SelectAndClassifyPairs(fxSet)
		C = {}
		for onecpt in self.conceptSet:		# consider all concept, get different length of intent
			#i = onecpt.intent.getLength()
			i = len(onecpt.intent)
			if C.has_key(i):
				C[i].append(onecpt)
			else:
				C[i] = [onecpt]
		#C = self.SelectAndClassifyPairs(fxSet)
		CAnother = {}						# 初始化所有更新格节点和所有新生成的格节点的集合为空
		C_list = C.keys()
		C_list.sort()		
		for i in C_list:							# 从intent的数目由小到大考虑各个概念
			for H in C[i]:
				if H.intent <= fxSet:
				#if fxSet.contains(H.intent):		# 这个概念的intent包含于fxSet，需要修改这个概念
					H.addExtent(xSet)				# 加入对象xSet，相应的CAnother也得加入
					if CAnother.has_key(i):
						CAnother[i].append(H)
					else:
						CAnother[i] = [H]
					#if H.intent.isEqual(fxSet):	#若这个概念的intent＝fxSet，exit algorithm
					if H.intent == fxSet:
						return True
				else:											# 这个概念的intent不包含于fxSet，old concept
					intSet = H.intent.intersection(fxSet)		# 得到这个概念的intent和fxSet的交集
					#len_intSet = intSet.getLength()
					len_intSet = len(intSet)
					flaglist = 0						# 存在标志，要表达出：在更新概念CAnother且intent的势为交集势的概念中
					if CAnother.has_key(len_intSet):	# 不存在一更新概念的intent＝这个交集
						flaglist = len([H1 for H1 in CAnother[len_intSet] if H1.intent == intSet])
						#flaglist = len([H1 for H1 in CAnother[len_intSet] if H1.intent.isEqual(intSet)])
					if not flaglist:								# not exisit, show that H is a generator
						Hn = Concept(H.extent.union(xSet), intSet)	# new concept:extent+xSet, intSet
						self.addConcept(Hn)
						if CAnother.has_key(len_intSet):			# add this new concept to CAnother
							CAnother[len_intSet].append(Hn)
						else:
							CAnother[len_intSet] = [Hn]
						self.addEdge(Hn, H)				# add new edge: from this new concept to current
						jlist = [j for j in range(0, len_intSet) if CAnother.has_key(j)]	# modify edges
						for j in jlist:														# 找更新概念中
							for Ha in CAnother[j]:
								#if intSet.contains(Ha.intent):				# Ha is a potential parent of Hn 
								if Ha.intent <= intSet:	
									parent = True
									child_of_Ha = Ha.child_list
									for Hd in child_of_Ha:
										#if intSet.contains(Hd.intent):		# 只要有一个更新概念的孩子的intent包含于这个交集，
										if Hd.intent <= intSet:
											parent = False			# 就不是父节点
											break
									# end for
									if parent:						# 是父节点
										if H in child_of_Ha:		# 当前的概念是不是他的孩子，是的话，删除边
											self.deleteEdge(Ha, H)	# eliminate edge Ha->H
										self.addEdge(Ha, Hn)		# 增加父节点到这个新概念的边
									# end if
								# end if
							# end for
						# end for
						if intSet == fxSet:							# 哪种情况？？
							return True
						# end if
					# end if
				# end if
			# end for
		# end for
	# end Add

	def __str__(self):
		selfstring = 'Concept------>>\n'
		for cpt in self.conceptSet:
			#selfstring += '%s\n' % cpt%s)\n%s\nhis layer is: %d
			selfstring += 'Concept %d: {' % cpt.conceptID
			for obj in cpt.extent:
			    #selfstring += ' %s ' % self.object_list[obj]
				selfstring += ' obj_%d_%d ' % (obj[0], obj[1])
			selfstring += '}, {'
			
			for i in cpt.intent:
			    selfstring += ' %s ' % self.attribute_list[i]
			selfstring += '}\n    his children is: '

			for one in cpt.child_list:
				selfstring += ' %d ' % one.conceptID	
			selfstring += '\n     his parent is: '

			for one in cpt.parent_list:
				selfstring += ' %d ' % one.conceptID	
			selfstring += '\n'

		for onelayer_key in self.layer.keys():
			tmp = 'concept is:'
			for cpt in self.layer[onelayer_key]:
				tmp += '%d ' % cpt.conceptID
			selfstring += "\nlayer %d: %s\n" % (onelayer_key, tmp)
		return selfstring

class Concept:
	conceptNUM = 0

	#def __init__(self, extent=set(), intent=Bin_Set.BSet()):
	def __init__(self, extent=set(), intent=set()):
		Concept.conceptNUM += 1
		self.conceptID = Concept.conceptNUM
		self.extent = extent
		self.intent = intent
		self.child_list = []
		self.parent_list = []
		self.layer_num = 0
		
		self.xigema = 0
		self.lamna = 0
		T_len = len(extent)
		E_len = len(intent)
		if T_len:
			for one in self.extent:
				self.xigema = 0
		if E_len:
			self.lamna = 0

	def addIntent(self, intentSet):
		self.intent = self.intent.union(intentSet)

	def addExtent(self, extentSet):
		self.extent = self.extent.union(extentSet)

	def __str__(self):
		str_child = 'his children is: '
		for one in self.child_list:
			str_child += '%d, ' % one.conceptID
		return 'Concept %d: (%s), (%s)\n%s\nhis layer is: %d' % (self.conceptID, self.extent, self.intent, str_child[:-2], self.layer_num)
