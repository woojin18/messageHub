(function($){
  $(document).ready(function(){
    //외부 영역 클릭시 닫기
    //$(document).click(function (e){
    //	var container = $("#userGnb .list_gnb > li > ul.dep2");
    //	if( container.has(e.target).length === 0)
    //	container.slideUp('fast');
    //});
  
    //gnb
    /*
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
    */
    $(document).on('mouseover', '#userGnb .list_gnb > li > a', function(e) {
      $('#userGnb .list_gnb > li > ul.dep2').hide();
      $(this).next('ul.dep2').slideDown('fast');
    });
    $(document).on('focusin', '#userGnb .list_gnb > li > a', function(e) {
      $('#userGnb .list_gnb > li > ul.dep2').hide();
      $(this).next('ul.dep2').slideDown('fast');
    });
    $(document).on('mouseleave', '#userGnb .list_gnb', function(e) {
      $('#userGnb .list_gnb > li > ul.dep2').slideUp('fast');
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
    /* 
    $('.faqList > li').click(function() {
      $('.faqList > li').removeClass('active');
      $(this).addClass('active');
    });
     */
    $(document).on('click', '.faqList > li', function(e) {
      $('.faqList > li').removeClass('active');
      $(this).addClass('active');
    });
  });
  
})(jQuery);