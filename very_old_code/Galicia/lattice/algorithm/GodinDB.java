package lattice.algorithm;

import handledata.connectDB;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import lattice.util.BadInputDataException;
import lattice.util.BinaryRelation;
import lattice.util.DefaultFormalAttribute;
import lattice.util.DefaultFormalObject;
import lattice.util.FormalObject;
import lattice.util.Intent;
import lattice.util.SetIntent;

public class GodinDB {

	connectDB db;

	private BinaryRelation br;

	Vector visited_cs;

	int old_concept_num = 0; // 存放概念数目，也就是格节点数目

	int new_concept_num = 0; // 当概念格更新变化以后，存放新的节点数目


	
	
	public GodinDB(BinaryRelation br) {
		db = new connectDB();
		this.br = br;
		
	}

	public void addConcept(Intent intent) {
		Statement stmt = null;

		int parrent_id = -1;
		old_concept_num = new_concept_num;
		visited_cs = new Vector();
		for (int i = 1; i <= old_concept_num; i++) { // i将代表概念编号
			String sql = "select Iid,name from itable limit " + (i - 1) + ",1";
			ResultSet rs;
			String intent_string = "";
			Intent current_intent = new SetIntent();
			try {
				stmt = db.conn.createStatement();
				rs = stmt.executeQuery(sql);
				rs.next();
				parrent_id = Integer.valueOf(rs.getString(1).trim()).intValue();
				if (rs.getString(2) == null) {
					current_intent.add(new DefaultFormalAttribute(""));
				} else {
					intent_string = rs.getString(2).trim();
					String[] a = intent_string.split(" ");
					for (int j = 0; j < a.length; j++)
						current_intent.add(new DefaultFormalAttribute(a[j]));
				}
			} catch (SQLException e) {
				System.out.println("每次读取内涵错误！");
			}

			if (current_intent.contains(new DefaultFormalAttribute(""))) { // 此处用contains或者equal都是一样，用来区分是否是第一root节点
				concept con = new concept(parrent_id, current_intent);
				visited_cs.add(con);
			} else if (current_intent.intersection(intent).equals(
					current_intent)) { // 更新概念
				// concept 在本类中，定义的一个特殊概念结构
				concept con = new concept(parrent_id, current_intent);
				visited_cs.add(con);
			} else {
				Intent new_intent = current_intent.intersection(intent);
				// 调用存储过程来自动判断是否可以新增该节点
				if (new_intent.size() >= 1) {
					new_intent.add(new DefaultFormalAttribute(""));
					String field_att = "";
					Iterator iter = new_intent.iterator();
					int temp = 0;
					int getResult = -1; // 接收从functin传过来的值，如果为0，则表示已经存在概念，如果为1，则表示增加了一个概念
					int current_id = -1; // 如果有新增id，那么获得其在表中的概念id号
					while (iter.hasNext()) {
						field_att = field_att
								+ " "
								+ ((DefaultFormalAttribute) iter.next())
										.getName().trim();
					}
					try {
						// by cjj 2006.6.21 利用procedure来接受返回参数的方法
						temp = new_intent.size() - 1;
						CallableStatement proc = db.conn
								.prepareCall("{ call add_concept(?,?,?,?) }");
						proc.setString(1, field_att.trim());
						proc.setInt(2, temp);
						proc.execute();
						getResult = proc.getInt(3);
						current_id = proc.getInt(4);
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("新增一个概念时失败");
					}

					if (getResult == 1) { // 成功新增一个节点
						// 增加边,此处需要获得parrent在表中的id，以及新增节点的id
						// old_concept_num++;
						new_concept_num++;

						try {
							// 此处因为addedge中有触发,所以出错过

							CallableStatement proc = db.conn
									.prepareCall("{ call addEdge(?,?,?) }");
							proc.setInt(2, parrent_id);
							proc.setInt(1, current_id);
							proc.execute();
						} catch (SQLException e) {
							System.out.println("新增一个边失败");
						}

						concept con = new concept(current_id, current_intent);
						visited_cs.add(con);

						// 对visited_cs中的节点进行处理
						for (int j = 0; j < visited_cs.size(); j++) {

							concept conce = (concept) visited_cs.get(j);
							if (new_intent.containsAll(conce.getIntent())) {
								// 可以前台处理，也可以后台处理
								// 前台处理就是对数据表edgetable进行扫描，找出visited_cs.get(j)的child，比较child是否属于new——intent
								boolean chd = true;
								concept c = (concept) visited_cs.get(j);
								sql = "select Childrenid from edgetable where Fatherid="
										+ c.getID();
								int child = c.getID();
								try {
									Statement stmt_3 =null;
									stmt_3 = db.conn.createStatement();
									ResultSet rs_2;
									rs_2 = stmt_3.executeQuery(sql);
									while (rs_2.next()) {
										child = rs_2.getInt(1);
										if (child != current_id) {
											ResultSet _rs;
											Statement stmt_2 =null;
											String _sql="select name from itable where Iid="+child;
											stmt_2 = db.conn.createStatement();
											_rs = stmt_2.executeQuery(_sql);
											_rs.next();
											String _string = _rs.getString(1)
													.trim();
											String[] b = _string.split(" ");
											Intent child_intent = new SetIntent();
											for (int k = 0; k < b.length; k++)
												child_intent
														.add(new DefaultFormalAttribute(
																b[k]));

											if (new_intent
													.containsAll(child_intent)) {
												chd = true;
												break;
											}
										}
									}
								} catch (SQLException e) {
									e.printStackTrace();
									System.out.println("修改边操作，读取edgetable异常");
								}
								if (chd == true) {
									try {
										CallableStatement proc = db.conn
												.prepareCall("{ call addEdge(?,?,?) }");
										proc.setInt(1, c.getID());
										proc.setInt(2, current_id);
										proc.execute();
										int edge_count = proc.getInt(3);
										proc = db.conn
												.prepareCall("{ call deleteEdge(?) }");
										proc.setInt(1, edge_count);
										proc.execute();
									} catch (SQLException e) {
										System.out.println("增加边发现异常");

									}
								}
							}
						}

					}

				}
			}
		}

	}

	public void doAlgorithm() {
		/*
		 * 从数据库中读取形式背景 int current_cur=0; //记录 int get_p_size=100; //控制每次取多少记录
		 * int max_size=0; //保存当前形式背景得大小 try{
		 * 
		 * Statement stmt = db.conn.createStatement(); ResultSet rs =
		 * stmt.executeQuery("select count(*) from defaultname"); rs.next();
		 * //字符串型转换成整型 max_size=Integer.valueOf(rs.getString(1)).intValue(); //
		 * System.out.print(rs.getString(1)); // Do something with the
		 * Connection rs.close(); stmt.close();
		 * 
		 * 
		 * }catch (SQLException e){ System.out.println("统计形式背景大小错误!"); }
		 * 
		 * while(current_cur+get_p_size<max_size) { try { Statement stmt =
		 * db.conn.createStatement(); String sql="select * from defaultname
		 * where "+; ResultSet rs = stmt.executeQuery(sql); rs.next();
		 * }catch(SQLException e) { System.out.println("循环取记录失败！"); } }
		 */

		Statement stmt = null;
		ResultSet rs;
		try {
			stmt = db.conn.createStatement();
			rs = stmt.executeQuery("select node_number from ltable");
			rs.next();
			old_concept_num = Integer.valueOf(rs.getString(1)).intValue();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("统计concepts出错！");
		}

		if (old_concept_num == 0) { // 初始化整个格图,利用了存储过程
			Vector all_attr = br.getAttributes();
			String field_att = "";
			for (int i = 0; i < all_attr.size(); i++) {
				field_att = field_att + " " + all_attr.get(i);
			}
			try {
				CallableStatement proc = db.conn
						.prepareCall("{ call initial_lattice(?, ?) }");
				proc.setString(1, field_att);
				proc.setInt(2, all_attr.size());
				proc.execute();
			} catch (SQLException e) {
				System.out.println("初始化格失败！");
			}
			System.out.println("初始化完毕!");
		}
		new_concept_num = old_concept_num = 2;

		int nbObjets = br.getObjectsNumber(); // 取得形式背景得对象数目
		for (int i = 0; i < nbObjets; i++) {
			FormalObject o = new DefaultFormalObject(null);
			try {
				o = br.getFormalObject(i);
			} catch (BadInputDataException bide) {
				System.out.println("The indice parameter is not valid");
			}
			doGodin(br.getF(o));
		}
		System.out.println("成功！");
	}

	public void doGodin(Intent intent) {

		// Concept concept = new Concept(intent);
		addConcept(intent);
	}

	public String getDescription() {
		return "Godin incremental Lattice Algorithm";
	}
}

class concept {
	int id;

	Intent intent;

	public concept(int id, Intent intent) {
		this.id = id;
		this.intent = intent;
	}

	int getID() {
		return this.id;
	}

	Intent getIntent() {
		return this.intent;
	}
}
