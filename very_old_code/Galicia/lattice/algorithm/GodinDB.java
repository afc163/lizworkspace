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

	int old_concept_num = 0; // ��Ÿ�����Ŀ��Ҳ���Ǹ�ڵ���Ŀ

	int new_concept_num = 0; // ���������±仯�Ժ󣬴���µĽڵ���Ŀ


	
	
	public GodinDB(BinaryRelation br) {
		db = new connectDB();
		this.br = br;
		
	}

	public void addConcept(Intent intent) {
		Statement stmt = null;

		int parrent_id = -1;
		old_concept_num = new_concept_num;
		visited_cs = new Vector();
		for (int i = 1; i <= old_concept_num; i++) { // i�����������
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
				System.out.println("ÿ�ζ�ȡ�ں�����");
			}

			if (current_intent.contains(new DefaultFormalAttribute(""))) { // �˴���contains����equal����һ�������������Ƿ��ǵ�һroot�ڵ�
				concept con = new concept(parrent_id, current_intent);
				visited_cs.add(con);
			} else if (current_intent.intersection(intent).equals(
					current_intent)) { // ���¸���
				// concept �ڱ����У������һ���������ṹ
				concept con = new concept(parrent_id, current_intent);
				visited_cs.add(con);
			} else {
				Intent new_intent = current_intent.intersection(intent);
				// ���ô洢�������Զ��ж��Ƿ���������ýڵ�
				if (new_intent.size() >= 1) {
					new_intent.add(new DefaultFormalAttribute(""));
					String field_att = "";
					Iterator iter = new_intent.iterator();
					int temp = 0;
					int getResult = -1; // ���մ�functin��������ֵ�����Ϊ0�����ʾ�Ѿ����ڸ�����Ϊ1�����ʾ������һ������
					int current_id = -1; // ���������id����ô������ڱ��еĸ���id��
					while (iter.hasNext()) {
						field_att = field_att
								+ " "
								+ ((DefaultFormalAttribute) iter.next())
										.getName().trim();
					}
					try {
						// by cjj 2006.6.21 ����procedure�����ܷ��ز����ķ���
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
						System.out.println("����һ������ʱʧ��");
					}

					if (getResult == 1) { // �ɹ�����һ���ڵ�
						// ���ӱ�,�˴���Ҫ���parrent�ڱ��е�id���Լ������ڵ��id
						// old_concept_num++;
						new_concept_num++;

						try {
							// �˴���Ϊaddedge���д���,���Գ����

							CallableStatement proc = db.conn
									.prepareCall("{ call addEdge(?,?,?) }");
							proc.setInt(2, parrent_id);
							proc.setInt(1, current_id);
							proc.execute();
						} catch (SQLException e) {
							System.out.println("����һ����ʧ��");
						}

						concept con = new concept(current_id, current_intent);
						visited_cs.add(con);

						// ��visited_cs�еĽڵ���д���
						for (int j = 0; j < visited_cs.size(); j++) {

							concept conce = (concept) visited_cs.get(j);
							if (new_intent.containsAll(conce.getIntent())) {
								// ����ǰ̨����Ҳ���Ժ�̨����
								// ǰ̨������Ƕ����ݱ�edgetable����ɨ�裬�ҳ�visited_cs.get(j)��child���Ƚ�child�Ƿ�����new����intent
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
									System.out.println("�޸ı߲�������ȡedgetable�쳣");
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
										System.out.println("���ӱ߷����쳣");

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
		 * �����ݿ��ж�ȡ��ʽ���� int current_cur=0; //��¼ int get_p_size=100; //����ÿ��ȡ���ټ�¼
		 * int max_size=0; //���浱ǰ��ʽ�����ô�С try{
		 * 
		 * Statement stmt = db.conn.createStatement(); ResultSet rs =
		 * stmt.executeQuery("select count(*) from defaultname"); rs.next();
		 * //�ַ�����ת�������� max_size=Integer.valueOf(rs.getString(1)).intValue(); //
		 * System.out.print(rs.getString(1)); // Do something with the
		 * Connection rs.close(); stmt.close();
		 * 
		 * 
		 * }catch (SQLException e){ System.out.println("ͳ����ʽ������С����!"); }
		 * 
		 * while(current_cur+get_p_size<max_size) { try { Statement stmt =
		 * db.conn.createStatement(); String sql="select * from defaultname
		 * where "+; ResultSet rs = stmt.executeQuery(sql); rs.next();
		 * }catch(SQLException e) { System.out.println("ѭ��ȡ��¼ʧ�ܣ�"); } }
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
			System.out.println("ͳ��concepts����");
		}

		if (old_concept_num == 0) { // ��ʼ��������ͼ,�����˴洢����
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
				System.out.println("��ʼ����ʧ�ܣ�");
			}
			System.out.println("��ʼ�����!");
		}
		new_concept_num = old_concept_num = 2;

		int nbObjets = br.getObjectsNumber(); // ȡ����ʽ�����ö�����Ŀ
		for (int i = 0; i < nbObjets; i++) {
			FormalObject o = new DefaultFormalObject(null);
			try {
				o = br.getFormalObject(i);
			} catch (BadInputDataException bide) {
				System.out.println("The indice parameter is not valid");
			}
			doGodin(br.getF(o));
		}
		System.out.println("�ɹ���");
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
