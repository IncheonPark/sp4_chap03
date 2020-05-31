package spring;

import java.util.Collection;

public class MemberListPrinter {
	
	private MemberDao memberDao;
	private MemberPrinter printer;
	
	public MemberListPrinter(MemberDao memberDao, MemberPrinter printer) {
		this.memberDao = memberDao;
		this.printer = printer;
	}
	
	public void printAll() {
		Collection<Member> members = memberDao.selectAll();
		
		// members에 멤버가 없을 시 예외 처리
		if (members == null || members.size() == 0) {
			throw new CollectionNoMemberException();
			
		}
		for (Member m: members) { //members안의 요소들을 한 개 씩 변수m에 저장. 향상된 for문
			printer.print(m);
		}
	}

}
