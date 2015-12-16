
public class Start {

	public static void main(String[] args) {

		VM vm = new VM();
		Assembler as = new Assembler();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		as.start(vm);
		for (int i = 0; i < vm.register.length; i++) {

			System.out.println(vm.register[i]);
		}

		
		System.out.println(vm.memory[0]);
		System.out.println(vm.memory[1]);
		System.out.println(vm.memory[2]);
		System.out.println(vm.memory[3]);

	}

}
