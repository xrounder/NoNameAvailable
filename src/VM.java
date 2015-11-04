import java.util.ArrayList;
import java.util.Stack;

public class VM {
	
	public final int NOP = 0;
	public final int LOAD = 1;
	public final int MOV = 2;
	public final int ADD = 3;
	public final int SUP = 4;
	public final int MUL = 5;
	public final int DIV = 6;
	public final int PUSH = 7;
	public final int POP = 8;
	public final int JMP = 9;
	public final int JIZ = 10;
	public final int JIH = 11;
	public final int JSR = 12;
	public final int RTS = 13;

	//Programmablaufspeicher
	int[] memory = new int[4096];
	//Register
	int[] register = new int[16];
	//Stack (für?)
	Stack<Integer> stack = new Stack<>();
	
	private int registerIndex = 0;

	
	//das GANZE
	private int opcode = 0;
	private int pcounter = 0;
	//Index von Rx
	private int idefix = 0;
	//Index von Ry
	private int idy = 0;
	private int fromMem = 0;
	private int toMem = 0;
	private int wert = 0;
	private int cmd = 0;
	private int pcounterLine = 0;

	void startVM() {
		
		do {

			splitOpcode();
			
			
			switch (cmd) {

			case NOP:
				pcounter++;
				break;
			case LOAD:				
				register[0]= wert;
				pcounter++; 
				break;
			case MOV:
				
				break;
			case ADD:
				register[idefix]+=register[idy];
				pcounter++;
				break;
			//chris
			case SUP:
				register[idefix]-=register[idy];
				pcounter++;
				break;
			//chris
			case MUL:
				register[idefix]*=register[idy];
				pcounter++;
				break;
			//chris
			case DIV:
				try{
				register[idefix]/=register[idy];
				}catch(Exception ex){
					ex.printStackTrace();					
				}
				pcounter++;
				break;
			//miguel
			case PUSH:
				stack.push(register[idefix]);
				pcounter++; 
				break;
			//miguel
			case POP:
				register[idefix] = stack.pop();
				pcounter++; 
				break;
			//miguel
			case JMP:
				pcounterLine = pcounter;
				pcounter = wert;
				
				break;
			//miguel
			case JIZ:
			if(register[0]==0){
				
				
			}
			break;
			
			//marcel
			case JIH:
				if(register[0]>0){
					
					
				}
				break;
			
			//marcel
			case JSR:break;
			//marcel
			case RTS:break;
			//marcel

			default:
				break;

			}
			
			
		} while (false);

	}

	private void splitOpcode() {
		
		opcode = memory[pcounter];
		
		cmd = opcode & 0xF;
		wert = (opcode >> 4);
		idefix = (opcode >> 4) & 0xF;
		idy = (opcode >> 8) & 0xF;
		fromMem = (opcode >> 12) & 0x1;
		toMem = (opcode >> 13) & 0x1;
		
	}
	

}
