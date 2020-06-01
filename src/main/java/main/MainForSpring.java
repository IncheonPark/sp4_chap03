package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import spring.AlreadyExistingMemberException;
import spring.ChangePasswordService;
import spring.CollectionNoMemberException;
import spring.IdPasswordNotMatchingException;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.NotPassEqualToConfirmException;
import spring.RegisterRequest;
import spring.VersionPrinter;

public class MainForSpring {
	
	private static ApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException {
		ctx = new GenericXmlApplicationContext("classpath:configImport.xml");
		//방법 1. 한 설정 파일 사용 시("classpath:applicationContext.xml");
		//방법 2. 두개 이상 설정 파일 사용 시 파라미터 입력("classpath:conf1.xml","classpath:conf2.xml");
		//방법 3. 두개 이상 설정 파일 사용 시 배열 변수 전달 Stiring[] conf = {"classpath:conf1.xml", "classpath:conf2.xml"};
		//방법 4. 한개의 설정 파일을 사용하고 그 설정 파일에 import 추가
		
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
			} else if (command.startsWith("list")) {
				processListCommand();
				continue;
			} else if (command.startsWith("info ")) {
				processInfoCommand(command.split(" "));
				continue;
			} else if (command.equals("version")) {
				processVersionCommand();
				continue;
			}
			printHelp();
		}
	}
	
	private static void processNewCommand(String[] args) {
		if (args.length != 5) {
			printHelp();
			return;
		}
		MemberRegisterService regSvc = ctx.getBean("memberRegSvc", MemberRegisterService.class);
		RegisterRequest req = new RegisterRequest();
		req.setEmail(args[1]);
		req.setName(args[2]);
		req.setPassword(args[3]);
		req.setConfirmPassword(args[4]);
		
		//isPasswordEqualToConfirmPassword()는 rgist(req)안에 넣음.
		
		try {
			regSvc.regist(req);
			System.out.println("등록했습니다. \n");
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
		ChangePasswordService changePwdSvc = ctx.getBean("changePwdSvc", ChangePasswordService.class);
		try {
			changePwdSvc.changePassword(args[1], args[2], args[3]);
			System.out.println("암호를 변경했습니다. \n");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다. \n");
		} catch (IdPasswordNotMatchingException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다. \n");
		}
	}
	
	private static void processListCommand() {
		MemberListPrinter listPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
		try {
			listPrinter.printAll();
			//조회할 멤버가 없을 시 예외 문구 출력.
		} catch (CollectionNoMemberException e) {
			System.out.println("조회할 회원이 없습니다.");
		}
	}
	
	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법: 입력 요소 사이에 공백을 한 칸씩 넣어 주세요.");
		System.out.println("사용자 등록 시 : new 이메일 이름 암호 암호확인");
		System.out.println("비밀 번호 변경 시 : change 이메일 현재비번 바꿀비번");
		System.out.println("회원 목록 조회 시 : list");
		System.out.println("회원 정보 조회 시 : info 이메일");
		System.out.println("버전 정보 조회 시 : version");
		System.out.println();
	}
	
	private static void processInfoCommand(String[] args) {
		if(args.length != 2) {
			printHelp();
			return;
		}
		MemberInfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		infoPrinter.printMemberInfo(args[1]);
	}
	
	private static void processVersionCommand() {
		VersionPrinter versionPrinter = ctx.getBean("versionPrinter", VersionPrinter.class);
		versionPrinter.print();
	}

}
