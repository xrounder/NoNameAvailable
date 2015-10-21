
public class Start {

	public static void main(String[] args) {
		
		VM vm = new VM();
		
		vm.memory[0] = vm.ADD + (0 << 4) + (1 << 8);
		
		System.out.println(vm.memory[0]);
		vm.startVM();

	}

}
