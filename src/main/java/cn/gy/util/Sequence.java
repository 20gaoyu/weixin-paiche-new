package cn.gy.util;

import tk.mybatis.mapper.genid.GenId;

/**
 * 生成随机数
 * 
 * @author Administrator
 *
 */
public class Sequence implements GenId<Long> {
//	static String querySeqSql = "select seq_ngbilling.nextval";

//	public static String nextVal(Connection conn, String domain) {
//		String nextVal = DBTools.selectAsString(conn, querySeqSql);
//		long seqVal = new Long("200000000000000000").longValue() + Long.parseLong(nextVal);
//		return String.valueOf(seqVal);
//	}

//	public static String nextVal(String domain) {
//		return String.format("3%d%s", System.currentTimeMillis(), KeyGen.uuid(4, 0, 9));
//	}

	public static String nextVal() {
		return String.format("3%d%s", System.currentTimeMillis(), KeyGen.uuid(4, 0, 9));
	}
	
	public static String randomNum() {
		return String.format(KeyGen.uuid(20, 0, 61));
	}

	public static Long nextLong() {
		return Long.parseLong(nextVal());
	}

	/**
	 * 
	 * @param idLen id的长度
	 * @return
	 */
	public static String nextVal(int idLen) {
		return String.format(KeyGen.uuid(idLen, 0, 9));
	}

	@Override
	public Long genId(String table, String column) {
		return nextLong();
	}
}
