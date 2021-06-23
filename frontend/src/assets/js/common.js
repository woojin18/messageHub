export default {
  install(Vue){
    Vue.prototype.$gfnCommonUtils = {
      /**************************문자열 관련 Utils**************************/
      //빈 문자열 확인
      isEmpty(str){
        if( str == "" || str == null || str == undefined || ( str != null && typeof str == "object" && !Object.keys(str).length)) return true;
        else return false ;
      },
      //str이 빈값이 아니면 str 리턴 빈값이면 defaultStr 리턴
      defaultIfEmpty(str, defaultStr){
          defaultStr = (this.isEmpty(defaultStr) ? '' : defaultStr);
          return (this.isEmpty(str) ? defaultStr : str);
      },
      //lpad
      lpad(str, padLen, padStr) {
        if (padStr.length > padLen) return str;
        str += ""; // 문자로
        padStr += ""; // 문자로
        while (str.length < padLen) str = padStr + str;
        str = str.length >= padLen ? str.substring(0, padLen) : str;
        return str;
      },
      newLineToBr(str){
        if(this.isEmpty(str)) return str;
        return str.replace(/(?:\r\n|\r|\n)/g, '<br>');
      },
      /**************************문자열 관련 Utils**************************/
      /**************************날짜관련 관련 Utils**************************/
      //현재일자 return
      getCurretDate : function(format){
        format = this.defaultIfEmpty(format, 'yyyy-mm-dd');
        return this.formatDate(new Date(), format);
      },
      //포맷에 맞는 날짜 return
      formatDate(date, format) {
        const map = {
          mm: this.lpad(date.getMonth() + 1, 2, '0'),
          dd: this.lpad(date.getDate(), 2, '0'),
          yy: date.getFullYear().toString().slice(-2),
          yyyy: date.getFullYear().toString()
        }
        return format.replace(/mm|dd|yyyy|yy/gi, matched => map[matched]);
      },
      //날짜(일) 더하기
      strDateAddDay(dateStr, interval){
        const dateParts = dateStr.split('-');
        const sDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
        sDate.setDate(sDate.getDate()+interval);
        return this.formatDate(sDate, 'yyyy-mm-dd');
      },
      //날짜(월) 더하기
      strDateAddMonth(dateStr, interval){
        const dateParts = dateStr.split('-');
        const sDate = new Date(dateParts[0], dateParts[1] - 1, dateParts[2]);
        sDate.setMonth(sDate.getMonth()+interval);
        return this.formatDate(sDate, 'yyyy-mm');
      },
      /**************************날짜관련 관련 Utils**************************/
      /**************************정규식 관련 Utils**************************/
      isUrl(str){
        let regex = /(http(s)?:\/\/)([a-z0-9\w]+\.*)+[a-z0-9]{2,4}/gi
        return regex.test(str);
      },
      /**************************정규식 관련 Utils**************************/
    }
  }
}