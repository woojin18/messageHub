$(document).ready(function() {

    var lang_kor = {
        "decimal" : "",
        "emptyTable" : "데이터가 없습니다.",
        "info" : "전체 <strong>_TOTAL_<strong>건",
        "infoEmpty" : "0명",
        "infoFiltered" : "(전체 _MAX_ 명 중 검색결과)",
        "infoPostFix" : "",
        "thousands" : ",",
        "lengthMenu" : "_MENU_ 개씩 보기",
        "loadingRecords" : "로딩중...",
        "processing" : "처리중...",
        "search" : "검색 : ",
        "zeroRecords" : "검색된 데이터가 없습니다.",
        "paginate" : {
            "first" : "첫 페이지",
            "last" : "마지막 페이지",
            "next" : "다음",
            "previous" : "이전"
        },
        "aria" : {
            "sortAscending" : " :  오름차순 정렬",
            "sortDescending" : " :  내림차순 정렬"
        }
    };



	//대시보드 테이블1
	$('#dashTable1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );


	//대시보드 테이블2
	$('#dashTable2').DataTable( {
		"scrollX": true,		//가로스크롤
		"scrollY": "240px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );

	//메뉴관리 테이블1
	$('#menuTable1').DataTable( {
		"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": false,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );


	//메뉴관리 테이블3
	$('#menuTable3').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": true,		//정보 표시
		language: lang_kor		//한글
	} );

	//고객사관리 테이블1
	$('#client1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": true,		//정보 표시
		language: lang_kor		//한글
	} );

	//고객사관리 테이블2
	$('#client2').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": true,		//정보 표시
		language: lang_kor		//한글
	} );

	//고객별 상품단가 관리 테이블1
	$('#product1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": true,		//정보 표시
		language: lang_kor		//한글
	} );

	//서비스채널관리 테이블1
	$('#channel1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": true,		//정보 표시
		language: lang_kor		//한글
	} );


	//admin 비트큐브 대시보드 테이블1
	$('#admin_dashTable1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );

	//admin 사용자 관리 테이블1
	$('#admin_User1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": true,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": true,		//정보 표시
		language: lang_kor		//한글
	} );

	
} );