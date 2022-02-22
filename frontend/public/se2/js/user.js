(function($){
  $(document).ready(function(){
    //외부 영역 클릭시 닫기
    //$(document).click(function (e){
    //	var container = $("#userGnb .list_gnb > li > ul.dep2");
    //	if( container.has(e.target).length === 0)
    //	container.slideUp('fast');
    //});
  
    //gnb
 //gnb
	  $('#userGnb .list_gnb > li > a').mouseover(function() {
		//event.stopPropagation();
	  	$('#userGnb .list_gnb > li > ul.dep2').hide();
		  $(this).next('ul.dep2').slideDown('fast');
	  });
	  $('#userGnb .list_gnb > li > a').focusin(function() {
	  	$('#userGnb .list_gnb > li > ul.dep2').hide();
	  	$(this).next('ul.dep2').slideDown('fast');
	  });
	  $('#userGnb .list_gnb').mouseleave(function() {
	  	$('#userGnb .list_gnb > li > ul.dep2').slideUp('fast');
	  });

	// fixed 
	  var s = $("header.head_type_user");
	  var pos = s.position();          
	  $(window).scroll(function() {
	  	var windowpos = $(window).scrollTop();
	  	if (windowpos >= pos.top) {
	  		s.addClass("fixed");
	  	} else {
		  	s.removeClass("fixed"); 
	  	}
	  	if (windowpos <= pos.top) {
	  		s.removeClass("fixed"); 
	  	} else {
		  	s.addClass("fixed");
		  }
	  });

      //quickRight
  	$(window).scroll(function(){
          var y = $(this).scrollTop();
          if(y >= 560){
            $('.quickRight').addClass('fix');
          }
         else {
              $('.quickRight').removeClass('fix');
         }
      });

  	//faqList
  	$('.faqList > li').click(function() {
	  	$('.faqList > li').removeClass('active');
	  	$(this).addClass('active');
  	});
  });
  
})(jQuery);