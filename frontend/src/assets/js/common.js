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
      unescapeXss(str){
        if(this.isEmpty(str)) return str;
        let unescapedStr = str.replace(/&gt;/g, '>')
                              .replace(/&lt;/g, '<')
                              .replace(/&#40;/g, '(')
                              .replace(/&#41;/g, ')')
                              .replace(/&#39;/g, "'");
        return unescapedStr;
      },
      unescapeXssFields(targetObj, fields){
        if(fields == null || Array.isArray(fields) == false || fields.length == 0) return targetObj;
        fields.forEach(field => {
          targetObj[field] = this.unescapeXss(targetObj[field]);
        });
      },
      /**************************정규식 관련 Utils**************************/
      /**************************비즈니스 관련 Utils**************************/
      //ch - SMS, MMS, RCS, ALIMTALK, FRIENDTALK, PUSH
      //str - 검사내용
      //return - result = {
      //            success: ture/false(유효성 통과여부)
      //            message: '', '변수 phone, title, description 은 예약어로 사용하실 수 없습니다.'(통과시 '', 실패시 '' 또는 해당 메시지)
      //          }
      validContainRsvNm(ch, str){
        let result = {
          success: true,
          message: ''
        }

        if(this.isEmpty(ch) || this.isEmpty(str)){
          result.success = false;
          result.message = '시스템 오류입니다. 잠시후 다시 시도해주세요.';
          console.error('validContainRsvNm 잘못된 파라미터 정보 ==> ch: '+ch+', str : '+str);
          return result;
        }

        let rsvNmSet;
        if(ch == 'PUSH'){
          rsvNmSet = new Set(['cuid', 'phone', 'title', 'description']);
        } else if(ch == 'SMS' || ch == 'MMS' || ch == 'ALIMTALK' || ch == 'FRIENDTALK'){
          rsvNmSet = new Set(['phone', 'title', 'description']);
        } else if(ch == 'RCS'){
          rsvNmSet = new Set(['phone']);
        }

        if(!rsvNmSet || rsvNmSet.length == 0){
          result.success = false;
          result.message = '시스템 오류입니다. 잠시후 다시 시도해주세요.';
          console.error('validContainRsvNm 잘못된 채널정보입니다. ==> ch: '+ch+', rsvNmSet : '+rsvNmSet);
          return result;
        }

        if(ch == 'RCS'){
          str.replace(/\{\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}\}/g, function($0, $1) {
            if(rsvNmSet.has($1)){
              result.success = false;
              return false;
            }
          });
        } else {
          str.replace(/#\{(([a-z|A-Z|0-9|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|_])+)\}/g, function($0, $1) {
            if(rsvNmSet.has($1)){
              result.success = false;
              return false;
            }
          });
        }
        
        if(result.success == false){
          const rsvNmStr = Array.from(rsvNmSet).join(', ');
          result.message = '변수 '+rsvNmStr+' 은 예약어로 사용하실 수 없습니다.';
        }
        
        return result;
      },
      /**************************비즈니스 관련 Utils**************************/
    }
  }
}