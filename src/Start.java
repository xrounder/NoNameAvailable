
public class Start {

	public static void main(String[] args) {

		VM vm = new VM();
		Assembler as = new Assembler();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		as.start(vm);
		
		for (int i = 0; i < vm.register.length; i++) {

			System.out.println("Register["+i+"] = "+vm.register[i]);
		}

		for (int i = 1000; i < 1020; i++) {

			System.out.println("Memory["+i+"] = "+vm.memory[i]);
		}
		

	}

}
