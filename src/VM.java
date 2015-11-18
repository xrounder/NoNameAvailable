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
	//Stack
	FastVector stack = new FastVector(25000000,1000);
	FastVector routineStack = new FastVector(25000000, 1000);
	
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
	private int stackCounter = 0;
	private int routineCounter = 0;

	void startVM() {
		
		do {

			splitOpcode();
			
			
			switch (cmd) {

			//does absolutely nothing!
			case NOP:
				pcounter++;
				break;
				
			//Load value in R(0)	
			case LOAD:				
				register[0]= wert;
				pcounter++; 
				break;
				
			// Move, flagged with fromMem & toMem (Operate in Register/Memory !)	
			case MOV:
				if (fromMem ==0 && toMem ==0){
					register[idefix]=register[idy];
				}else if (fromMem==1 && toMem ==0){
					register[idefix]=memory[idy];
				}else if (fromMem ==0 && toMem ==1){
					memory[idefix]=register[idy];
				}else //fromMem ==1 && toMem == 1
				{
					memory[idefix]=memory[idy];
					
				}
				
				break;
			//add R(X) = R(X)+R(Y)	
			case ADD:
				register[idefix]+=register[idy];
				pcounter++;
				break;
				
			//subtract R(X)=R(X)-R(Y)
			case SUP:
				register[idefix]-=register[idy];
				pcounter++;
				break;
				
			//Multiply R(X)=R(X)*R(Y)
			case MUL:
				register[idefix]*=register[idy];
				pcounter++;
				break;
				
			//Divide R(X)=R(X)/R(Y)
			case DIV:
				try{
				register[idefix]/=register[idy];
				}catch(Exception ex){
					ex.printStackTrace();					
				}
				pcounter++;
				break;
				
			//Push the Value in R(X) on the Stack
			case PUSH:
				stack.setValue(stackCounter++, (register[idefix]));
				pcounter++; 
				break;
				
			//Pop a Value of the Stack, save it in R(X)
			case POP:
				register[idefix] = stack.getValue(stackCounter--);
				pcounter++; 
				break;
				
			//Jump the f*ck up!
			case JMP:
				pcounter = wert;
				
				break;
				
			//Jump (only if Register[0] is exactly 0!)
			case JIZ:
			if(register[0]==0){
				pcounter = wert;
				
			}
			break;
			
			//Jump (only if Register[0] is > 0!)
			case JIH:
				if(register[0]>0){
					pcounter = wert;
					
				}
				break;
			
			//Jump to Subroutine(pcounter push on stack!)
			case JSR:
				routineStack.setValue(routineCounter++, pcounter);
				pcounter=wert;
				
				break;
				
			//Return from Subroutine(getting pcounter per pop from stack!)
			case RTS:
				pcounter=routineStack.getValue(routineCounter--);
				
	
				break;
			

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
