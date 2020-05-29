package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import assembler.Assembler;
import spring.AlreadyExistingMemberException;
import spring.ChangePasswordService;
import spring.IdPasswordNotMatchingException;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.NotPassEqualToConfirmException;
import spring.RegisterRequest;

public class MainForAssembler {

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.println("명령어를 입력하세요.");
			String command = reader.readLine();
			
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			
			if (command.startsWith("new ")) {
				processNewCommand(command.split(" "));
				continue;
			} else if (command.startsWith("change ")) {
				processChangeCommand(command.split(" "));
				continue;
			}
			printHelp();
		}	

	} //---------------- 메인 메서드 끝
	
	private static Assembler assembler = new Assembler();
	
	private static void processNewCommand(String[] args) {
		if (args.length != 5) {
			printHelp();
			return;
		}
		MemberRegisterService regSvc = assembler.getMemberRegisterService();
		RegisterRequest req = new RegisterRequest();
		req.setEmail(args[1]);
		req.setName(args[2]);
		req.setPassword(args[3]);
		req.setConfirmPassword(args[4]);
		
		/*if (!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("암호와 확인이 일치하지 않습니다. \n");
			return; 
		} 기본 예제 코드 주석처리하고 regist() 안에 로직 추가 해서 익셉션 새로운거 한 개 던지도록 만들어 봄.*/
		
		try {
			regSvc.regist(req);
			System.out.println("등록 했습니다. \n");
			
		} catch (NotPassEqualToConfirmException e) {
			System.out.println("암호와 암호확인이 일치하지 않습니다. \n");
			
		} catch (AlreadyExistingMemberException e) {
			System.out.println("이미 존재하는 이메일입니다. \n");
		}
	}
	
	private static void processChangeCommand(String[] args) {
		if (args.length != 4) {
			printHelp();
			return;
		}
		ChangePasswordService changePwdSvc = assembler.getChangePasswordService();
		try {
			changePwdSvc.changePassword(args[1], args[2], args[3]);
			System.out.println("암호를 변경했습니다.");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다. \n");
		} catch (IdPasswordNotMatchingException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다. \n");
		}
	}
	
	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법: 입력 요소 사이에 공백을 한 칸씩 넣어 주세요.");
		System.out.println("사용자 등록 시 : new 이메일 이름 암호 암호확인");
		System.out.println("비밀 번호 변경 시 : change 이메일 현재비번 바꿀비번");
		System.out.println();
	}

}
