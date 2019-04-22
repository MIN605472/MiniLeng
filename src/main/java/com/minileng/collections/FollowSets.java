package com.minileng.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Follow sets of the grammar production generated with ANAGRA.
 */
public class FollowSets {

  private static final Map<String, Set> followSets = new HashMap<>();

  static {
    {
      Set<String> s = new HashSet<>();
      s.add("EOF");
      followSets.put("programa", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tACCION");
      s.add("tPRINCIPIO");
      followSets.put("declaracion_variables", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      followSets.put("declaracion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tID");
      followSets.put("tipo_variables", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tPAR_DCHA");
      followSets.put("identificadores", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tPAR_DCHA");
      followSets.put("repeticion_identificadores", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPRINCIPIO");
      followSets.put("declaracion_acciones", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tACCION");
      s.add("tPRINCIPIO");
      followSets.put("declaracion_accion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      followSets.put("cabecera_accion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      followSets.put("parametros_formales", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPAR_DCHA");
      followSets.put("lista_parametros", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tPAR_DCHA");
      followSets.put("parametro", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPAR_DCHA");
      followSets.put("repeticion_parametro", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tENTERO");
      s.add("tCARACTER");
      s.add("tBOOLEANO");
      followSets.put("clase_parametros", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("EOF");
      s.add("tACCION");
      s.add("tPRINCIPIO");
      followSets.put("bloque_sentencias", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("lista_sentencias", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("repeticion_lista_sentencias", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("sentencia", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      followSets.put("leer", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      followSets.put("escribir", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("asignacion_invocacion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("asignacion_invocacion_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("asignacion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("invocacion_accion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("mientras_que", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("seleccion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      followSets.put("argumentos_accion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tCOMA");
      followSets.put("argumento_escribir", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPAR_DCHA");
      followSets.put("lista_argumentos_vacia", s);
    }
    {
      Set<String> s = new HashSet<>();
      followSets.put("lista_argumentos_no_vacia", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPAR_DCHA");
      followSets.put("lista_argumentos_escribir", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPAR_DCHA");
      followSets.put("repeticion_argumentos", s);
    }
    {
      Set<String> s = new HashSet<>();
      followSets.put("repeticion_argumentos_escribir", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tKENTERO");
      s.add("tID");
      s.add("tKCARACTER");
      s.add("tPAR_IZQ");
      s.add("tCARAENT");
      s.add("tENTACAR");
      followSets.put("operador_relacional", s);
    }
    {
      Set<String> s = new HashSet<>();
      followSets.put("caraent", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMUL");
      s.add("tDIV");
      s.add("tDIVCHAR");
      s.add("tMOD");
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("entacar", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("expresion_bool", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("term_bool", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("factor_bool_unario", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("factor_bool", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("relacion", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("expresion_arit", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("term_arit", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMUL");
      s.add("tDIV");
      s.add("tDIVCHAR");
      s.add("tMOD");
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("factor_arit_unario", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMUL");
      s.add("tDIV");
      s.add("tDIVCHAR");
      s.add("tMOD");
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("factor_arit", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("expresion_bool_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("term_bool_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("expresion_arit_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("term_arit_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("seleccion_", s);
    }
    {
      Set<String> s = new HashSet<>();
      followSets.put("caraent_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("relacion_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMUL");
      s.add("tDIV");
      s.add("tDIVCHAR");
      s.add("tMOD");
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("factor_arit_", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("seleccion__", s);
    }
    {
      Set<String> s = new HashSet<>();
      followSets.put("caraent__", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tMUL");
      s.add("tDIV");
      s.add("tDIVCHAR");
      s.add("tMOD");
      s.add("tMAS");
      s.add("tMENOS");
      s.add("tMAYOR");
      s.add("tMENOR");
      s.add("tIGUAL");
      s.add("tMAI");
      s.add("tMEI");
      s.add("tNI");
      s.add("tAND");
      s.add("tOR");
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tCOMA");
      s.add("tPAR_DCHA");
      s.add("tENT");
      followSets.put("factor_arit__", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("seleccion___", s);
    }
    {
      Set<String> s = new HashSet<>();
      s.add("tPUNTOCOMA");
      s.add("tLEER");
      s.add("tESCRIBIR");
      s.add("tID");
      s.add("tSI");
      s.add("tMQ");
      s.add("tFIN");
      s.add("tFMQ");
      s.add("tFSI");
      s.add("tSI_NO");
      followSets.put("seleccion____", s);
    }
  }

  public static Set getFollowSet(String nonterminal) {
    return followSets.get(nonterminal);
  }

}
