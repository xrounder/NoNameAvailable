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
	public int getRegisterIndex() {
		return registerIndex;
	}

	public void setRegisterIndex(int registerIndex) {
		this.registerIndex = registerIndex;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public int getPcounter() {
		return pcounter;
	}

	public void setPcounter(int pcounter) {
		this.pcounter = pcounter;
	}

	public int getIdefix() {
		return idefix;
	}

	public void setIdefix(int idefix) {
		this.idefix = idefix;
	}

	public int getIdy() {
		return idy;
	}

	public void setIdy(int idy) {
		this.idy = idy;
	}

	public int getFromMem() {
		return fromMem;
	}

	public void setFromMem(int fromMem) {
		this.fromMem = fromMem;
	}

	public int getToMem() {
		return toMem;
	}

	public void setToMem(int toMem) {
		this.toMem = toMem;
	}

	public int getWert() {
		return wert;
	}

	public void setWert(int wert) {
		this.wert = wert;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

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
				//chris
				break;
			case ADD:break;
			//chris
			case SUP:break;
			//chris
			case MUL:break;
			//chris
			case DIV:
				register[idefix]+=register[idy];
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
			case JIZ:break;
			//marcel
			case JIH:break;
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
