
public class Start {

	public static void main(String[] args) {
		
		VM vm = new VM();
		
		vm.memory[0] = vm.LOAD + (600 << 4);
		
		vm.memory[1] = vm.MOV + (0 << 4) + (5 << 8);
		
	
		vm.startVM();
		System.out.println(vm.register[0]);

	}

}
