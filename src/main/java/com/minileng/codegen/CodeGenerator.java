package com.minileng.codegen;

import java.io.FileWriter;
import java.io.IOException;

public class CodeGenerator {

  private int labelNum;

  private int instrCount;

  private StringBuffer sb;

  public CodeGenerator() {
    labelNum = 0;
    instrCount = 0;
    sb = new StringBuffer();
  }

  public void reinit() {
    labelNum = 0;
    instrCount = 0;
    sb.delete(0, sb.length());
  }

  public int getInstrCount() {
    return instrCount;
  }

  public String newLabel() {
    return "L" + labelNum++;
  }

  public void writeToFile(String filename) throws IOException {
    FileWriter fileWriter = new FileWriter(filename);
    fileWriter.write(sb.toString());
    fileWriter.close();
  }

  public void comment(String msg) {
    sb.append("; ").append(msg).append('\n');
  }

  public void enp(String label) {
    ++instrCount;
    sb.append('\t');
    sb.append("ENP ").append(label).append('\n');
  }

  public void lvp() {
    ++instrCount;
    sb.append('\t');
    sb.append("LVP").append('\n');
  }

  public void jmp(String n) {
    ++instrCount;
    sb.append('\t');
    sb.append("JMP ").append(n).append('\n');
  }

  public void jmt(String n) {
    ++instrCount;
    sb.append('\t');
    sb.append("JMT ").append(n).append('\n');
  }

  public void jmf(String n) {
    ++instrCount;
    sb.append('\t');
    sb.append("JMF ").append(n).append('\n');
  }

  public void osf(int s, int l, int a) {
    ++instrCount;
    sb.append('\t');
    sb.append("OSF ").append(s).append(" ").append(l).append(" ").append(a).append('\n');
  }

  public void csf() {
    ++instrCount;
    sb.append('\t');
    sb.append("CSF\n");
  }

  public void rd(boolean integer) {
    ++instrCount;
    sb.append('\t');
    sb.append("RD ");
    if (integer) {
      sb.append("1");
    } else {
      sb.append("0");
    }
    sb.append('\n');
  }

  public void wrt(boolean integer) {
    ++instrCount;
    sb.append('\t');
    sb.append("WRT ");
    if (integer) {
      sb.append("1");
    } else {
      sb.append("0");
    }
    sb.append('\n');
  }

  public void srf(int frame, int offset) {
    ++instrCount;
    sb.append('\t');
    sb.append("SRF ").append(frame).append(" ").append(offset).append('\n');
  }

  public void stc(int n) {
    ++instrCount;
    sb.append('\t');
    sb.append("STC ").append(n).append('\n');
  }

  public void drf() {
    ++instrCount;
    sb.append('\t');
    sb.append("DRF\n");
  }

  public void asg() {
    ++instrCount;
    sb.append('\t');
    sb.append("ASG\n");
  }

  public void asgi() {
    ++instrCount;
    sb.append('\t');
    sb.append("ASGI\n");
  }

  public void plus() {
    ++instrCount;
    sb.append('\t');
    sb.append("PLUS\n");
  }

  public void sbt() {
    ++instrCount;
    sb.append('\t');
    sb.append("SBT\n");
  }

  public void tms() {
    ++instrCount;
    sb.append('\t');
    sb.append("TMS\n");
  }

  public void mod() {
    ++instrCount;
    sb.append('\t');
    sb.append("MOD\n");
  }

  public void div() {
    ++instrCount;
    sb.append('\t');
    sb.append("DIV\n");
  }

  public void ngi() {
    ++instrCount;
    sb.append('\t');
    sb.append("NGI\n");
  }

  public void nop() {
    ++instrCount;
    sb.append('\t');
    sb.append("NOP\n");
  }

  public void swp() {
    ++instrCount;
    sb.append('\t');
    sb.append("SWP\n");
  }

  public void dup() {
    ++instrCount;
    sb.append('\t');
    sb.append("DUP\n");
  }

  public void and() {
    ++instrCount;
    sb.append('\t');
    sb.append("AND\n");
  }

  public void or() {
    ++instrCount;
    sb.append('\t');
    sb.append("OR\n");
  }

  public void eq() {
    ++instrCount;
    sb.append('\t');
    sb.append("EQ\n");
  }

  public void neq() {
    ++instrCount;
    sb.append('\t');
    sb.append("NEQ\n");
  }

  public void lt() {
    ++instrCount;
    sb.append('\t');
    sb.append("LT\n");
  }

  public void lte() {
    ++instrCount;
    sb.append('\t');
    sb.append("LTE\n");
  }

  public void gt() {
    ++instrCount;
    sb.append('\t');
    sb.append("GT\n");
  }

  public void gte() {
    ++instrCount;
    sb.append('\t');
    sb.append("GTE\n");
  }

  public void ngb() {
    ++instrCount;
    sb.append('\t');
    sb.append("NGB\n");
  }

  public void insertLabel(String l) {
    sb.append(l).append(":\n");
  }

  @Override
  public String toString() {
    return sb.toString();
  }

}
