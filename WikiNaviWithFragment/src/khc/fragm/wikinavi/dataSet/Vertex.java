package khc.fragm.wikinavi.dataSet;

public class Vertex {
	private int vertexId;
	private String name;
	private int x, y;
	private String type;
	private String macAddr;
	
	public int getVertexId() {
		return vertexId;
	}
	public void setVertexId(int vertexId) {
		this.vertexId = vertexId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
}
