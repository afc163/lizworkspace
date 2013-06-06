#!/usr/bin/python
#coding=utf-8
# Filename : Lattice.py 

class Lattice:

	def __init__(self, seqDB):
		'''initialize the Lattice obj_list=[], attr_list=[]'''
		self.conceptSet = []					#概念的集合
		self.supConcept = Concept()
		self.addConcept(self.supConcept)
		self.botConcept = Concept()
		self.addConcept(self.botConcept)
		self.layer = {}
		
		self.object_list = seqDB.object_list
		self.attribute_list = seqDB.attribute_list
		
	def addConcept(self, newconcept):
		'''add a new concept to conceptSet'''
		self.conceptSet.append(newconcept)
		
	def addEdge(self, parent_cpt, child_cpt):
		'''add a new edge to edgeSet'''
		parent_cpt.child_list.append(child_cpt)
		child_cpt.parent_list.append(parent_cpt)
				
		#if parent_cpt.layer_num >= child_cpt.layer_num + 1: #don't change laynum
		#	pass
		#else:
		#	if parent_cpt in self.layer[parent_cpt.layer_num]: #remove old layer
		#		self.layer[parent_cpt.layer_num].remove(parent_cpt)
		#	parent_cpt.layer_num = child_cpt.layer_num + 1
		#	if self.layer.has_key(parent_cpt.layer_num):
				#if not parent_cpt in self.layer[parent_cpt.layer_num]:
				#	self.layer[parent_cpt.layer_num].append(parent_cpt)
		#		self.layer[parent_cpt.layer_num].append(parent_cpt)
		#	else:
		#		self.layer[parent_cpt.layer_num] = [parent_cpt]
		#	self.updateLayer(parent_cpt)
	
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
	def equal(self, a, b):
		len_a = len(a)
		len_b = len(b)
		for index in range(0, min(len_a, len_b)):
		    if a[index][0] != b[index][0]:
			    return False
		return True 
	def big_equal(self, a, b):
		len_a = len(a)
		len_b = len(b)
		if len_a != len_b:
		    return False
		for item in a:
		    if not self.contains(b, item):
			    return False
		return True 
	def contains(self, big, small):
		for item in big:
			if self.equal(item, small):
			    return True
		return False
	def big_contains(self, a, b):
		for item in b:
			if not self.contains(a, item):
				return False
		return True
	def union(self, a, b):
		for i in a:
			if self.equal(i, b):
				return a
		a.append(b)
		return a
	def get_Set(self, a):
		rs = []
		rs.append(a)
		queue = []
		queue.append(a)
		while queue:
			one_len = len(queue[0])
			#print queue[0]
			for index in range(0, one_len):
				tmp = list(queue[0])
				del tmp[index]
				if tmp and tmp not in rs:
					rs.append(tmp)
					queue.append(tmp)
			del queue[0]
		#print 'nmbn',rs
		return rs
	def contain_other(self, a, b):
		for i in b:
			f = False
			for j in a:
			    if i[0] == j[0]:
					f = True
					break
			if not f:
			    return False
		return True
	def intersection(self, a, b):
		result = []
		for item in a:
			item_len = len(item)
			b_len = len(b)
			if item_len < b_len:
				set = self.get_Set(item)
				for tt in set:
				    if self.contain_other(b, tt):
						result.append(tt)
						break
			else:
				set = self.get_Set(b)
				for tt in set:
					if self.contain_other(item, tt):
						result.append(tt)
						break
		return result
	def intersection1(self, a, b):
		result = []
		for item in a:
			sign = False
			item_len = len(item)
			b_len = len(b)
			if item_len < b_len:
				set = self.get_Set(item)
				for tt in set:
				    if self.contain_other(b, tt):
						result.append(tt)
						sign = True
						break
			else:
				set = self.get_Set(b)
				for tt in set:
					if self.contain_other(item, tt):
						result.append(tt)
						sign = True
						break
			if sign:
			    break
		return result
	def addOneObject(self, oneObject, oneattribute):
		'''Add a new object'''
		print '-------------oneobject', oneObject
		xSet = set([oneObject])						# the set of oneobject's extent
		#fxSet = Bin_Set.BSet(oneattribute)			# the set of oneobject's intent
		fxSet = list(oneattribute)	
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
		#if not fxSet <= self.supConcept.intent:
		if not self.contains(self.supConcept.intent, fxSet):
			if not self.supConcept.extent:			# supConcept's extent is none
				self.supConcept.addIntent(fxSet)	##?# add this object's intent to supConcept's
			else:
				t_intent = self.union(self.supConcept.intent, fxSet)
				H = Concept(set(), t_intent)##?# creat new concept: {},{supConcept.intent+fxSet
				self.addConcept(H)
				#H.layer_num = 0
				#self.layer[0] = [H]
				
				#self.supConcept.layer_num = 1
				#self.layer[1] = [self.supConcept]
				#self.botConcept.layer_num = 2
				#self.layer[2] = [self.botConcept]
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
			i = len(onecpt.intent)##?
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
				#if H.intent <= fxSet:##?
				if self.big_contains([fxSet], H.intent):		# 这个概念的intent包含于fxSet，需要修改这个概念
					H.addExtent(xSet)				# 加入对象xSet，相应的CAnother也得加入
					if CAnother.has_key(i):
						CAnother[i].append(H)
					else:
						CAnother[i] = [H]
					#if H.intent.isEqual(fxSet):	#若这个概念的intent＝fxSet，exit algorithm
					#if H.intent == fxSet:##?
					if self.big_equal(H.intent, [fxSet]):
						return True
				else:											# 这个概念的intent不包含于fxSet，old concept
					#intSet = H.intent.intersection(fxSet)##?		# 得到这个概念的intent和fxSet的交集
					intSet = self.intersection(H.intent, fxSet)
					#len_intSet = intSet.getLength()
					len_intSet = len(intSet)##?
					flaglist = 0						# 存在标志，要表达出：在更新概念CAnother且intent的势为交集势的概念中
					if CAnother.has_key(len_intSet):	# 不存在一更新概念的intent＝这个交集
						#flaglist = len([H1 for H1 in CAnother[len_intSet] if H1.intent == intSet])##?
						flaglist = len([H1 for H1 in CAnother[len_intSet] if self.big_equal(H1.intent, intSet)])
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
								if self.big_contains(intSet, Ha.intent):	##?
									parent = True
									child_of_Ha = Ha.child_list
									for Hd in child_of_Ha:
										#if intSet.contains(Hd.intent):		# 只要有一个更新概念的孩子的intent包含于这个交集，
										#if Hd.intent <= intSet:##?
										if self.big_contains(intSet, Hd.intent):
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
						#if intSet == fxSet:##?							# 哪种情况？？
						if self.big_equal(intSet, [fxSet]):
							return True
						# end if
					# end if
				# end if
			# end for
		# end for
	# end Add

	def seqenceCount(self):
		import time
		onetime = time.time()
		seq = {}
		for onecpt in self.conceptSet:
			count = len(onecpt.extent)
			for oneseq in onecpt.intent:
				str_oneseq = str(oneseq)
				if seq.has_key(str_oneseq) and seq[str_oneseq] < count:
					seq[str_oneseq] = count
				elif not seq.has_key(str_oneseq):
				    seq[str_oneseq] = count
		print 'the number of sequence is:', len(seq)
		all_seq_time = time.time() - onetime
		
		print all_seq_time
		sup_count = 0
		start = time.time()
		min_sup = int(0.09*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime
		sup_count = 0
		start = time.time()
		min_sup = int(0.08*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime		
		sup_count = 0
		start = time.time()
		min_sup = int(0.07*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime		
		sup_count = 0
		start = time.time()
		min_sup = int(0.06*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime		
		sup_count = 0
		start = time.time()
		min_sup = int(0.05*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime		
		sup_count = 0
		start = time.time()
		min_sup = int(0.04*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime		
		sup_count = 0
		start = time.time()
		min_sup = int(0.03*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime		
		sup_count = 0
		start = time.time()
		min_sup = int(0.02*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime		
		start = time.time()
		min_sup = int(0.01*len(self.object_list))
		for (key, item) in seq.items():
		    if item > min_sup:
			    sup_count += 1
		print '>%d:%d' % (min_sup, sup_count)
		sup_count = 0
		usetime = time.time() - start
		print all_seq_time+usetime			
		
	def __str__(self):
		selfstring = 'Concept------>>\n'
		for cpt in self.conceptSet:
			#selfstring += '%s\n' % cpt%s)\n%s\nhis layer is: %d
			selfstring += 'Concept %d: {' % cpt.conceptID
			for obj in cpt.extent:
			    #selfstring += ' %s ' % self.object_list[obj]
				selfstring += ' obj_%s ' % obj
			selfstring += '}, {'
			
			for i in cpt.intent:
			    selfstring += ' %s ' % i
			selfstring += '}\n    his children is: '

			#for one in cpt.child_list:
			#	selfstring += ' %d ' % one.conceptID	
			#selfstring += '\n     his parent is: '

			#for one in cpt.parent_list:
			#	selfstring += ' %d ' % one.conceptID	
			#selfstring += '\n'

		#for onelayer_key in self.layer.keys():
		#	tmp = 'concept is:'
		#	for cpt in self.layer[onelayer_key]:
		#		tmp += '%d ' % cpt.conceptID
		#	selfstring += "\nlayer %d: %s\n" % (onelayer_key, tmp)
		return selfstring

class Concept:
	conceptNUM = 0
	def __init__(self, extent=set(), intent=[]):
		Concept.conceptNUM += 1
		self.conceptID = Concept.conceptNUM
		self.extent = extent
		self.intent = intent
		self.child_list = []
		self.parent_list = []
		self.layer_num = 0

	def equal(self, a, b):
		len_a = len(a)
		len_b = len(b)
		for index in range(0, min(len_a, len_b)):
		    if a[index][0] != b[index][0]:
			    return False
		return True
	def addIntent(self, intent):
		for i in self.intent:
			if self.equal(i, intent):
				return
		self.intent.append(intent)

	def addExtent(self, extentSet):
		self.extent = self.extent.union(extentSet)

	def __str__(self):
		str_child = 'his children is: '
		for one in self.child_list:
			str_child += '%d, ' % one.conceptID
		return 'Concept %d: (%s), (%s)\n%s\nhis layer is: %d' % (self.conceptID, self.extent, self.intent, str_child[:-2], self.layer_num)
