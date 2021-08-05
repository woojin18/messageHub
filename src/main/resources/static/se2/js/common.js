$(document).ready(function(){
	//draggable
	jQuery( function() {
		jQuery(".draggable").draggable();
	} );
});

//addList
$(document).ready(function(){	
	setInterval(function(){
		jQuery('.addList_plus > a').click(function() {
			jQuery(this).children('a > .addIcon').attr("class","fal fa-minus-square addIcon");
			jQuery(this).parent('li').attr("class","addList_minus");
		});
		jQuery('.addList_minus > a').click(function() {
			jQuery(this).children('a > .addIcon').attr("class","fal fa-plus-square addIcon");
			jQuery(this).parent('li').attr("class","addList_plus");
		});
	}, 100);
});



//핸드폰 스크롤
$(document).ready(function() {
    jQuery(window).scroll(function(){
        var y = jQuery(this).scrollTop();
        if(y >= 100){
           jQuery('.phoneFixed').css('position','fixed').css('top','40px');
        }
        else {
            jQuery('.phoneFixed').css('position','relative').css('top','0px');
        }
    });
});

(function (w) {
  w.URLSearchParams = w.URLSearchParams || function (searchString) {
      var self = this;
      self.searchString = searchString;
      self.get = function (name) {
          var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(self.searchString);
          if (results == null) {
              return null;
          }
          else {
              return decodeURI(results[1]) || 0;
          }
      };
  }
})(window)