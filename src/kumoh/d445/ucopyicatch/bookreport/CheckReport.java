package kumoh.d445.ucopyicatch.bookreport;

import java.util.ArrayList;

import kumoh.d445.ucopyicatch.check.SuspendPlagiarismResultData;

public class CheckReport {
	private int reportid;
	private String date;
	private int studentNumber;
	private String name;
	private int opcheck;
	private double copyrate;
	private String title;
	private String text;
	private int code;
	private int type;
	private int eval;
	private ArrayList<String> report = new ArrayList<String>();
	private SuspendPlagiarismResultData copySentece = new SuspendPlagiarismResultData();
	
	public int getEval() {
		return eval;
	}
	public void setEval(int eval) {
		this.eval = eval;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getReportid() {
		return reportid;
	}
	public void setReportid(int reportid) {
		this.reportid = reportid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public ArrayList<String> getReport() {
		return report;
	}
	public SuspendPlagiarismResultData getCopySentece() {
		return copySentece;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpcheck() {
		return opcheck;
	}
	public void setOpcheck(int opcheck) {
		this.opcheck = opcheck;
	}
	public double getCopyrate() {
		return copyrate;
	}
	public void setCopyrate(double copyrate) {
		this.copyrate = copyrate;
	}
	
}
