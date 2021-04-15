(function($){
	$(document).ready(function(){
		//외부 영역 클릭시 닫기
		$(document).click(function (e){
			var container = $(".depth2Lnb.thum");
			if( container.has(e.target).length === 0)
			container.fadeOut('fast');
			$('#sidebar.thum > ul > li.active > a').css('background','#fff');
			//$('#sidebar > ul > li > a .navArrow').attr("class","far fa-chevron-down navArrow");
		});
		/* $('#sidebar > ul > li > a').click(function() {
			event.stopPropagation();
			$('.depth2Lnb').hide();
			$('#sidebar > ul > li > a .navArrow').attr("class","far fa-chevron-down navArrow");
			$(this).next('.depth2Lnb').fadeToggle();
			$('#sidebar > ul > li').removeClass('active');
			$(this).parent('li').addClass('active');
			$(this).children('span .navArrow').attr("class","far fa-chevron-up navArrow");
	
			if ($('#sidebar > ul > li.active').length == 1) {
				$(this).children('.navArrow').attr("class","far fa-chevron-up navArrow");
			}
			else {
				$('#sidebar > ul > li > a .navArrow').attr("class","far fa-chevron-down navArrow");
			}
		}); */
		
		//외부 영역 클릭시 닫기
		$(document).click(function (e){
			var container = $(".consoleMenu");
			if( container.has(e.target).length === 0)
			container.fadeOut('fast');
		});
		$('#sidebar > dl > dt > a').click(function() {
			event.stopPropagation();
			$('.consoleMenu').fadeToggle();
			$('#sidebar > dl > dt').removeClass('active');
			$(this).parent('li').addClass('active');
		});
	
		//datepicker
		$( function() {
			//$( ".datepicker" ).datepicker();
		} );
	
		//draggable
		$( function() {
			$(".draggable").draggable();
		} );
	});
	
	
	//sideMenu
	$(document).ready(function(){	
		$('.SideMenuOff').click(function() {
			setTimeout(function()
			{
				$('#sidebar').addClass('thum');
				$('.depth2Lnb').addClass('thum');
				$('#sidebar').css('width','70px');
				$('#sidebar > ul > li > a .navIcon').css('margin-right','0px');
				$('#sidebar > ul > li > a').css('text-align','center');
				$('#sidebar > dl > dt > a > span').hide();
				$('#sidebar > ul > li > a > span').hide();
				$('.SideMenuOff').hide();
				$('.navArrow').hide();
				$('.SideMenuOn').show();
			}, 300);
		});
	
		$('.SideMenuOn').click(function() {
			setTimeout(function()
			{
				$('#sidebar').removeClass('thum');
				$('.depth2Lnb').removeClass('thum');
				$('#sidebar').css('width','230px');
				$('#sidebar > ul > li > a .navIcon').css('margin-right','10px');
				$('#sidebar > ul > li > a').css('text-align','left');
				$('#sidebar > dl > dt > a > span').show();
				$('#sidebar > ul > li > a > span').show();
				$('.SideMenuOn').hide();
				$('.navArrow').show();
				$('.SideMenuOff').show();		
			}, 300);
		});		
	});

	//addList
	$(document).ready(function(){	
		setInterval(function(){
			$('.addList_plus > a').click(function() {
				$(this).children('a > .addIcon').attr("class","fal fa-minus-square addIcon");
				$(this).parent('li').attr("class","addList_minus");
			});
			$('.addList_minus > a').click(function() {
				$(this).children('a > .addIcon').attr("class","fal fa-plus-square addIcon");
				$(this).parent('li').attr("class","addList_plus");
			});
		}, 100);
	});
	
})(jQuery);