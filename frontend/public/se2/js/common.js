$(document).ready(function(){
	//외부 영역 클릭시 닫기
	jQuery(document).click(function (e){
		var container = jQuery(".depth2Lnb.thum");
		if( container.has(e.target).length === 0)
		container.fadeOut('fast');
		jQuery('#sidebar.thum > ul > li.active > a').css('background','#fff');
		//$('#sidebar > ul > li > a .navArrow').attr("class","far fa-chevron-down navArrow");
	});
	jQuery('#sidebar > ul > li > a').click(function() {
		event.stopPropagation();
		jQuery('.depth2Lnb').hide();
		jQuery('#sidebar > ul > li > a .navArrow').attr("class","far fa-chevron-down navArrow");
		jQuery(this).next('.depth2Lnb').fadeToggle();
		jQuery('#sidebar > ul > li').removeClass('active');
		jQuery(this).parent('li').addClass('active');
		jQuery(this).children('span .navArrow').attr("class","far fa-chevron-up navArrow");

		if (jQuery('#sidebar > ul > li.active').length == 1) {
			jQuery(this).children('.navArrow').attr("class","far fa-chevron-up navArrow");
		}
		else {
			jQuery('#sidebar > ul > li > a .navArrow').attr("class","far fa-chevron-down navArrow");
		}
	});
	jQuery('.depth2Lnb > ul > li > a').click(function() {
		jQuery('.depth3Lnb').hide();
		jQuery(this).next('.depth3Lnb').fadeToggle();
		jQuery('.depth2Lnb > ul > li').removeClass('active');
		jQuery(this).parent('li').addClass('active');
	});
	
	//외부 영역 클릭시 닫기
	jQuery(document).click(function (e){
		var container = jQuery(".consoleMenu");
		if( container.has(e.target).length === 0)
		container.fadeOut('fast');
	});
	jQuery('#sidebar > dl > dt > a').click(function() {
		event.stopPropagation();
		jQuery('.consoleMenu').fadeToggle();
		jQuery('#sidebar > dl > dt').removeClass('active');
		jQuery(this).parent('li').addClass('active');
	});

	//datepicker
	jQuery( function() {
		jQuery( ".datepicker" ).datepicker();
	} );
	//datepicker_gray
	jQuery( function() {
		jQuery( ".datepicker_gray" ).datepicker();
	} );

	//draggable
	jQuery( function() {
		jQuery(".draggable").draggable();
	} );
});


//sideMenu
$(document).ready(function(){	
	jQuery('.SideMenuOff').click(function() {
		setTimeout(function()
		{
			jQuery('#sidebar').addClass('thum');
			jQuery('.depth2Lnb').addClass('thum');
			jQuery('#sidebar').css('width','70px');
			jQuery('#sidebar > ul > li > a .navIcon').css('margin-right','0px');
			jQuery('#sidebar > ul > li > a').css('text-align','center');
			jQuery('#sidebar > dl > dt > a > span').hide();
			jQuery('#sidebar > ul > li > a > span').hide();
			jQuery('.SideMenuOff').hide();
			jQuery('.navArrow').hide();
			jQuery('.SideMenuOn').show();
		}, 300);
	});

	jQuery('.SideMenuOn').click(function() {
		setTimeout(function()
		{
			jQuery('#sidebar').removeClass('thum');
			jQuery('.depth2Lnb').removeClass('thum');
			jQuery('#sidebar').css('width','230px');
			jQuery('#sidebar > ul > li > a .navIcon').css('margin-right','10px');
			jQuery('#sidebar > ul > li > a').css('text-align','left');
			jQuery('#sidebar > dl > dt > a > span').show();
			jQuery('#sidebar > ul > li > a > span').show();
			jQuery('.SideMenuOn').hide();
			jQuery('.navArrow').show();
			jQuery('.SideMenuOff').show();		
		}, 300);
	});		
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