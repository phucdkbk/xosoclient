package com.alandk.xosomienbac.common;

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

    public String getGiaiNhi() {
        String result = "";
        for (int i = 0; i < arrGiaiNhi.length; i++) {
            if (i != arrGiaiNhi.length - 1) {
                result += arrGiaiNhi[i] + "-";
            } else {
                result += arrGiaiNhi[i];
            }
        }
        return result;
    }

    public String getGiaiBa() {
        String result = "";
        for (int i = 0; i < arrGiaiBa.length; i++) {
            if (i != arrGiaiBa.length - 1) {
                result += arrGiaiBa[i] + "-";
            } else {
                result += arrGiaiBa[i];
            }
        }
        return result;
    }

    public String getGiaiTu() {
        String result = "";
        for (int i = 0; i < arrGiaiTu.length; i++) {
            if (i != arrGiaiTu.length - 1) {
                result += arrGiaiTu[i] + "-";
            } else {
                result += arrGiaiTu[i];
            }

        }
        return result;
    }

    public String getGiaiNam() {
        String result = "";
        for (int i = 0; i < arrGiaiNam.length; i++) {
            if (i != arrGiaiNam.length - 1) {
                result += arrGiaiNam[i] + "-";
            } else {
                result += arrGiaiNam[i];
            }
        }
        return result;
    }

    public String getGiaiSau() {
        String result = "";
        for (int i = 0; i < arrGiaiSau.length; i++) {
            if (i != arrGiaiSau.length - 1) {
                result += arrGiaiSau[i] + "-";
            } else {
                result += arrGiaiSau[i];
            }
        }
        return result;
    }

    public String getGiaiBay() {
        String result = "";
        for (int i = 0; i < arrGiaiBay.length; i++) {
            if (i != arrGiaiBay.length - 1) {
                result += arrGiaiBay[i] + "-";
            } else {
                result += arrGiaiBay[i];
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

//    public void setHasFullValue(boolean hasFullValue) {
//        this.hasFullValue = hasFullValue;
//    }
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

}

