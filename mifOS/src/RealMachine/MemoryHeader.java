/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RealMachine;

public class MemoryHeader {
	public int next;
	public int prev;
	public int size;
	public int adr;
	
	MemoryHeader(int next, int prev, int size, int adr) {
		this.next = next;
		this.prev = prev;
		this.size = size;
		this.adr  = adr;
	}
	
	MemoryHeader() {
		
	}
}
