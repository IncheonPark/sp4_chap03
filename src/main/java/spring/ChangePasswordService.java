package spring;

public class ChangePasswordService {
	
	private MemberDao memberDao;
	
	public ChangePasswordService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null) //회원 정보가 비어 있으면
			throw new MemberNotFoundException();
		
		System.out.println("멤버의 비번: "+member.getPassword()); //비번 확인용(레지스터 서비스에서 멤버 객체 만들 때 생성자 파라미터 위치 다르게 놨었음.) 
		member.changePassword(oldPwd, newPwd);
		
		memberDao.update(member);
		
	}

}
