const coreUiMixin = {
  directives: {
    focus: {
      inserted: function(el) {
        el.focus();
      }
    }
  },
  methods: {
    clearInput: function(evt) {
      var me = evt.target;
      var vname = me.previousElementSibling.name;
      this.$data[vname] = '';
      me.previousElementSibling.focus();
    },
    showPhoneNumber: function(str) {
      str = str.replace(/[\-\s\/]+/g, '');
      if (str.length > 12 || str.length < 10) {
        return str;
      }
      var result =
        str.substring(0, str.length - 8) +
        '-' +
        str.substring(str.length - 8, str.length - 4) +
        '-' +
        str.substring(str.length - 4, str.length);
      return result;
    },
    /*
		openLayer: function(event, callback) {
			var myId = event.target.getAttribute('data-id');
			var myLayer = document.querySelector('[layer-id="' + myId + '"]');
			var btnClose = document.querySelector('[layer-id="' + myId + '"] .btn_close');
			myLayer.classList.add('active');
			btnClose.addEventListener("click", function() {
				myLayer.classList.remove('active');
				if (callback != undefined && callback != null) {
					callback('');
				}
			});

			if (callback != undefined && callback != null) {
				callback(myId);
			}
		},
		*/
    openLayer: function(layerId) {
      if (layerId == undefined || layerId == null || layerId == '') {
        alert('layerId를 설정해 주세요.');
      } else {
        this.$emit('changeLayerId', layerId);
      }
    },
    closeLayer() {
      this.$emit('changeLayerId', '');
    },
    numberComma(num) {
      if (typeof num == undefined || num == null) {
        return '';
      }
      num = num + '';
      let point = num.length % 3;
      let len = num.length;

      let str = num.substring(0, point);
      while (point < len) {
        if (str != '') {
          str += ',';
        }
        str += num.substring(point, point + 3);
        point += 3;
      }

      return str;
    }
  }
};

var chkPattern = {
  data: function() {
    return {};
  },
  methods: {
    setLenth: function(e, len) {
      this.cut(e, len);
    },
    onlyNum: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[0-9]*$/g.exec(str)) {
        e.target.value = str.replace(/[^0-9]/gi, '');
      }
    },
    only11Num: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[0-9]*$/g.exec(str)) {
        str = str.replace(/[^0-9]/gi, '');
      }

      if (str.length > 11) {
        str = str.substring(0, 11);
      }

      e.target.value = str;
    },
    onlyEmail: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[a-zA-Z0-9_\.\-@._-]*$/g.exec(str)) {
        e.target.value = str.replace(/[^a-zA-Z0-9_\.\-@._-]/gi, '');
      }
    },
    onlyName: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[ㄱ-힣a-zA-Z]*$/g.exec(str)) {
        e.target.value = str.replace(/[^ㄱ-힣a-zA-Z]/gi, '');
      }
    },
    onlyTitle: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[ㄱ-힣a-zA-Z0-9]*$/g.exec(str)) {
        e.target.value = str.replace(/[^ㄱ-힣a-zA-Z0-9]/gi, '');
      }
    },
    onlyText: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[ㄱ-힣a-zA-Z0-9_-]*$/g.exec(str)) {
        e.target.value = str.replace(/[^ㄱ-힣a-zA-Z0-9_-]/gi, '');
      }
    },
    onlyPassword: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[A-Za-z0-9!@#$%^&*]*$/g.exec(str)) {
        e.target.value = str.replace(/[^A-Za-z0-9!@#$%^&*]/gi, '');
      }
    },
    onlyId: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[A-Za-z0-9]*$/g.exec(str)) {
        e.target.value = str.replace(/[^A-Za-z0-9]/gi, '');
      }
    },
    onlyIp: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[0-9,.*]*$/g.exec(str)) {
        e.target.value = str.replace(/[^0-9,.*]/gi, '');
      }
    },
    onlyRoleNm_Space: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[ㄱ-힣a-zA-Z0-9/\s]*$/g.exec(str)) {
        e.target.value = str.replace(/[^ㄱ-힣a-zA-Z0-9/\s]/gi, '');
      }
    },
    onlyRoleId_UnderBar: function(e, len) {
      this.cut(e, len);
      var str = e.target.value;
      if (!/^[a-zA-Z0-9_]*$/g.exec(str)) {
        e.target.value = str.replace(/[^a-zA-Z0-9_]/gi, '');
      }
    },
    cut: function(e, len) {
      if (typeof len != 'undefined') {
        var str = e.target.value;
        if (this.bytes(str) > len) {
          e.target.value = this.cutBytes(str, len);
        }
      }
    },
    cutBytes: function(str, len) {
      var count = 0;
      for (var i = 0; i < str.length; i++) {
        if (escape(str.charAt(i)).length >= 4) count += 2;
        else if (escape(str.charAt(i)) != '%0D') count++;
        if (count > len) {
          if (escape(str.charAt(i)) == '%0A') i--;
          break;
        }
      }
      return str.substring(0, i);
    },
    bytes: function(str) {
      var length = 0;
      for (var i = 0; i < str.length; i++) {
        if (escape(str.charAt(i)).length >= 4) length += 2;
        else if (escape(str.charAt(i)) == '%A7') length += 2;
        else {
          if (escape(str.charAt(i)) != '%0D') length++;
        }
      }
      return length;
    },
    checkPhone: function(str) {
      str = str.replace(/[\-\s\/]+/g, '');
      if (str.charAt(0) != '0') {
        str = '0' + str;
      }
      if (str.length < 10 || str.length > 12) {
        return '';
      }
      if (isNaN(str)) {
        return '';
      }
      //if (str.substr(0,2)!="01" && str.substr(0,3)!="070"  && str.substr(0,4)!="0505" && str.substr(0,4)!="0503"){return "";	}
      //sif (str.substr(0,3)!="010"){return "";	}
      return str;
    },
    checkExcelFile: function(fileObj) {
      let reg = /(.*?)\.(xls|xlsx)$/;
      if (fileObj == undefined || fileObj == null)
        return false;
      if (!fileObj.name.match(reg)) 
        return false;
      
      return true;
    }
  }
};

export { coreUiMixin, chkPattern };
