package com.alandk.xosomienbac.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author phucdk
 */
public class Result {

	private String giaiDB;
	private String giaiNhat;
	private String[] arrGiaiNhi;
	private String[] arrGiaiBa;
	private String[] arrGiaiTu;
	private String[] arrGiaiNam;
	private String[] arrGiaiSau;
	private String[] arrGiaiBay;
	private boolean hasFullValue;

	private List<Integer> listDau0;
	private List<Integer> listDau1;
	private List<Integer> listDau2;
	private List<Integer> listDau3;
	private List<Integer> listDau4;
	private List<Integer> listDau5;
	private List<Integer> listDau6;
	private List<Integer> listDau7;
	private List<Integer> listDau8;
	private List<Integer> listDau9;

	public Result() {
		super();
		listDau0 = new ArrayList<Integer>();
		listDau1 = new ArrayList<Integer>();
		listDau2 = new ArrayList<Integer>();
		listDau3 = new ArrayList<Integer>();
		listDau4 = new ArrayList<Integer>();
		listDau5 = new ArrayList<Integer>();
		listDau6 = new ArrayList<Integer>();
		listDau7 = new ArrayList<Integer>();
		listDau8 = new ArrayList<Integer>();
		listDau9 = new ArrayList<Integer>();
	}

	public void caculateDauso() {
		if (giaiDB != null && !giaiDB.isEmpty()) {
			addToListDauso(giaiDB);
		}
		if (giaiNhat != null && !giaiNhat.isEmpty()) {
			addToListDauso(giaiNhat);
		}
		if (arrGiaiNhi != null) {
			for (int i = 0; i < arrGiaiNhi.length; i++) {
				addToListDauso(arrGiaiNhi[i]);
			}
		}
		if (arrGiaiBa != null) {
			for (int i = 0; i < arrGiaiBa.length; i++) {
				addToListDauso(arrGiaiBa[i]);
			}
		}
		if (arrGiaiTu != null) {
			for (int i = 0; i < arrGiaiTu.length; i++) {
				addToListDauso(arrGiaiTu[i]);
			}
		}
		if (arrGiaiNam != null) {
			for (int i = 0; i < arrGiaiNam.length; i++) {
				addToListDauso(arrGiaiNam[i]);
			}
		}
		if (arrGiaiSau != null) {
			for (int i = 0; i < arrGiaiSau.length; i++) {
				addToListDauso(arrGiaiSau[i]);
			}
		}
		if (arrGiaiBay != null) {
			for (int i = 0; i < arrGiaiBay.length; i++) {
				addToListDauso(arrGiaiBay[i]);
			}
		}
		Collections.sort(listDau0);
		Collections.sort(listDau1);
		Collections.sort(listDau2);
		Collections.sort(listDau3);
		Collections.sort(listDau4);
		Collections.sort(listDau5);
		Collections.sort(listDau6);
		Collections.sort(listDau7);
		Collections.sort(listDau8);
		Collections.sort(listDau9);
	}

	public String getDauso(List<Integer> listDau) {
		String duoiso = "";
		for (int i = 0; i < listDau.size(); i++) {
			if (i != listDau.size() - 1) {
				duoiso += listDau.get(i) + ",";
			} else {
				duoiso += listDau.get(i);
			}
		}
		return duoiso;
	}

	public String getDau0() {
		return getDauso(listDau0);
	}

	public String getDau1() {
		return getDauso(listDau1);
	}

	public String getDau2() {
		return getDauso(listDau2);
	}

	public String getDau3() {
		return getDauso(listDau3);
	}

	public String getDau4() {
		return getDauso(listDau4);
	}

	public String getDau5() {
		return getDauso(listDau5);
	}

	public String getDau6() {
		return getDauso(listDau6);
	}

	public String getDau7() {
		return getDauso(listDau7);
	}

	public String getDau8() {
		return getDauso(listDau8);
	}

	public String getDau9() {
		return getDauso(listDau9);
	}

	private void addToListDauso(String ketqua) {
		if(ketqua==null) return;
		if(ketqua.length()<2) return;
		// TODO Auto-generated method stub
		ketqua = ketqua.trim();
		int haisocuoi = Integer.valueOf(ketqua.substring(ketqua.length() - 2,
				ketqua.length()));
		switch (haisocuoi / 10) {
		case 0:
			listDau0.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 1:
			listDau1.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 2:
			listDau2.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 3:
			listDau3.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 4:
			listDau4.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 5:
			listDau5.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 6:
			listDau6.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 7:
			listDau7.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 8:
			listDau8.add(Integer.valueOf(haisocuoi % 10));
			break;
		case 9:
			listDau9.add(Integer.valueOf(haisocuoi % 10));
			break;
		}

	}

	public String getGiaiNhi() {
		String result = "";
		if (arrGiaiNhi != null) {
			for (int i = 0; i < arrGiaiNhi.length; i++) {
				if (i != arrGiaiNhi.length - 1) {
					result += arrGiaiNhi[i] + "-";
				} else {
					result += arrGiaiNhi[i];
				}
			}
		}

		return result;
	}

	public String getGiaiBa() {
		String result = "";
		if (arrGiaiBa != null) {
			for (int i = 0; i < arrGiaiBa.length; i++) {
				if (i != arrGiaiBa.length - 1) {
					result += arrGiaiBa[i] + "-";
				} else {
					result += arrGiaiBa[i];
				}
			}
		}

		return result;
	}

	public String getGiaiTu() {
		String result = "";
		if (arrGiaiTu != null) {
			for (int i = 0; i < arrGiaiTu.length; i++) {
				if (i != arrGiaiTu.length - 1) {
					result += arrGiaiTu[i] + "-";
				} else {
					result += arrGiaiTu[i];
				}

			}
		}

		return result;
	}

	public String getGiaiNam() {
		String result = "";
		if (arrGiaiNam != null) {
			for (int i = 0; i < arrGiaiNam.length; i++) {
				if (i != arrGiaiNam.length - 1) {
					result += arrGiaiNam[i] + "-";
				} else {
					result += arrGiaiNam[i];
				}
			}
		}

		return result;
	}

	public String getGiaiSau() {
		String result = "";
		if (arrGiaiSau != null) {
			for (int i = 0; i < arrGiaiSau.length; i++) {
				if (i != arrGiaiSau.length - 1) {
					result += arrGiaiSau[i] + "-";
				} else {
					result += arrGiaiSau[i];
				}
			}
		}
		return result;
	}

	public String getGiaiBay() {
		String result = "";
		if (arrGiaiBay != null) {
			for (int i = 0; i < arrGiaiBay.length; i++) {
				if (i != arrGiaiBay.length - 1) {
					result += arrGiaiBay[i] + "-";
				} else {
					result += arrGiaiBay[i];
				}
			}
		}
		return result;
	}

	public String getGiaiDB() {
		return giaiDB;
	}

	public void setGiaiDB(String giaiDB) {
		this.giaiDB = giaiDB;
	}

	public String getGiaiNhat() {
		return giaiNhat;
	}

	public void setGiaiNhat(String giaiNhat) {
		this.giaiNhat = giaiNhat;
	}

	public String[] getArrGiaiNhi() {
		return arrGiaiNhi;
	}

	public void setArrGiaiNhi(String[] arrGiaiNhi) {
		this.arrGiaiNhi = arrGiaiNhi;
	}

	public String[] getArrGiaiBa() {
		return arrGiaiBa;
	}

	public void setArrGiaiBa(String[] arrGiaiBa) {
		this.arrGiaiBa = arrGiaiBa;
	}

	public String[] getArrGiaiTu() {
		return arrGiaiTu;
	}

	public void setArrGiaiTu(String[] arrGiaiTu) {
		this.arrGiaiTu = arrGiaiTu;
	}

	public String[] getArrGiaiNam() {
		return arrGiaiNam;
	}

	public void setArrGiaiNam(String[] arrGiaiNam) {
		this.arrGiaiNam = arrGiaiNam;
	}

	public String[] getArrGiaiSau() {
		return arrGiaiSau;
	}

	public void setArrGiaiSau(String[] arrGiaiSau) {
		this.arrGiaiSau = arrGiaiSau;
	}

	public String[] getArrGiaiBay() {
		return arrGiaiBay;
	}

	public void setArrGiaiBay(String[] arrGiaiBay) {
		this.arrGiaiBay = arrGiaiBay;
	}

	public boolean isHasFullValue() {
		return hasFullValue;
	}

	// public void setHasFullValue(boolean hasFullValue) {
	// this.hasFullValue = hasFullValue;
	// }
	public void setHaveFullResult() {
		this.hasFullValue = checkHaveFullResult();
	}

	public boolean checkHaveFullResult() {
		if (this.giaiDB == null || this.giaiDB.isEmpty()) {
			return false;
		}
		if (this.giaiNhat == null || this.giaiNhat.isEmpty()) {
			return false;
		}
		if (this.arrGiaiNhi == null) {
			return false;
		}
		if (this.arrGiaiNhi != null) {
			for (String result : arrGiaiNhi) {
				if (result == null || result.isEmpty()) {
					return false;
				}
			}
		}

		if (this.arrGiaiBa == null) {
			return false;
		}
		if (this.arrGiaiBa != null) {
			for (String result : arrGiaiBa) {
				if (result == null || result.isEmpty()) {
					return false;
				}
			}
		}

		if (this.arrGiaiTu == null) {
			return false;
		}
		if (this.arrGiaiTu != null) {
			for (String result : arrGiaiTu) {
				if (result == null || result.isEmpty()) {
					return false;
				}
			}
		}

		if (this.arrGiaiNam == null) {
			return false;
		}
		if (this.arrGiaiNam != null) {
			for (String result : arrGiaiNam) {
				if (result == null || result.isEmpty()) {
					return false;
				}
			}
		}

		if (this.arrGiaiSau == null) {
			return false;
		}
		if (this.arrGiaiSau != null) {
			for (String result : arrGiaiSau) {
				if (result == null || result.isEmpty()) {
					return false;
				}
			}
		}

		if (this.arrGiaiBay == null) {
			return false;
		}
		if (this.arrGiaiBay != null) {
			for (String result : arrGiaiBay) {
				if (result == null || result.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	public List<Integer> getListDau0() {
		return listDau0;
	}

	public void setListDau0(List<Integer> listDau0) {
		this.listDau0 = listDau0;
	}

	public List<Integer> getListDau1() {
		return listDau1;
	}

	public void setListDau1(List<Integer> listDau1) {
		this.listDau1 = listDau1;
	}

	public List<Integer> getListDau2() {
		return listDau2;
	}

	public void setListDau2(List<Integer> listDau2) {
		this.listDau2 = listDau2;
	}

	public List<Integer> getListDau3() {
		return listDau3;
	}

	public void setListDau3(List<Integer> listDau3) {
		this.listDau3 = listDau3;
	}

	public List<Integer> getListDau4() {
		return listDau4;
	}

	public void setListDau4(List<Integer> listDau4) {
		this.listDau4 = listDau4;
	}

	public List<Integer> getListDau5() {
		return listDau5;
	}

	public void setListDau5(List<Integer> listDau5) {
		this.listDau5 = listDau5;
	}

	public List<Integer> getListDau6() {
		return listDau6;
	}

	public void setListDau6(List<Integer> listDau6) {
		this.listDau6 = listDau6;
	}

	public List<Integer> getListDau7() {
		return listDau7;
	}

	public void setListDau7(List<Integer> listDau7) {
		this.listDau7 = listDau7;
	}

	public List<Integer> getListDau8() {
		return listDau8;
	}

	public void setListDau8(List<Integer> listDau8) {
		this.listDau8 = listDau8;
	}

	public List<Integer> getListDau9() {
		return listDau9;
	}

	public void setListDau9(List<Integer> listDau9) {
		this.listDau9 = listDau9;
	}

	public void setHasFullValue(boolean hasFullValue) {
		this.hasFullValue = hasFullValue;
	}

}
