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
	

	//admin 테스트 프로젝트 테이블1
	$('#admin_testProject1').DataTable( {
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

	//admin 공지사항 테이블1
	$('#admin_notice1').DataTable( {
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

	//admin_sub02_1 사용자 알람 관리 테이블1
	$('#admin_sub02_1_User1').DataTable( {
		//"scrollX": true,		//가로스크롤
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
	
	//admin_sub02_1_2 사용자 알람 관리 테이블1
	$('#admin_sub02_2_User1').DataTable( {
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
	
	//admin_sub02_1_2 사용자 알람 관리 담당자 추가 테이블2
	$('#admin_sub02_2_User2').DataTable( {
		//"scrollX": true,		//가로스크롤
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

	//admin_sub03_1 프로젝트 관리 테이블1
	$('#admin_sub03_1_project1').DataTable( {
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

	//admin_sub03_4 멤버관리 테이블1
	$('#admin_sub03_4_member1').DataTable( {
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

	//admin_sub03_5_1_1_1 채널관리 테이블1
	$('#admin_sub03_5_1_1_Channel1').DataTable( {
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


	//admin_sub03_5_1_2 채널관리 테이블1
	$('#admin_sub03_5_1_2_Channel1').DataTable( {
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


	//admin_sub03_5_1_3 채널관리 테이블1
	$('#admin_sub03_5_1_3_Channel1').DataTable( {
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

	//admin_sub03_5_2 채널관리 테이블1
	$('#admin_sub03_5_2_Channel1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "100px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": false,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );



	//backOffie_sub09_1 권한 관리 (권한 메뉴 조회) 테이블1
	$('#backOffie_sub09_1').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "275px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": false,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );

	//backOffie_sub09_2 권한 관리 (권한 메뉴 조회) 테이블1
	$('#backOffie_sub09_2').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "275px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": false,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );


	//backOffice_sub10 권한 관리 (권한 메뉴 조회) 테이블2
	$('#backOffice_sub10').DataTable( {
		//"scrollX": true,		//가로스크롤
		"scrollY": "275px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": false,		//표시건수
		"paging": true,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": false,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );

	//backOffice_sub10_1 권한 관리 (권한 메뉴 조회) 테이블3
	$('#backOffice_sub10_1').DataTable( {
		//"scrollX": true,		//가로스크롤
		"scrollY": "275px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": false,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );

	//backOffice_sub11 권한 관리 (권한 메뉴 조회) 테이블3
	$('#backOffice_sub11').DataTable( {
		//"scrollX": true,		//가로스크롤
		//"scrollY": "275px",		//세로스크롤
		"dom": 'ilftp',
		"lengthChange": false,		//표시건수
		"paging": false,		//페이징
		"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
		"ordering": true,		//정렬
		"searching": false,		//검색
		"info": false,		//정보 표시
		language: lang_kor		//한글
	} );



	//backOffice_sub13 스마트 채널관리 테이블1
	$('#backOffice_sub13').DataTable( {
	//"scrollX": true,		//가로스크롤
	//"scrollY": "275px",		//세로스크롤
	"dom": 'ilftp',
	"lengthChange": false,		//표시건수
	"paging": false,		//페이징
	"pagingType": "full_numbers",		//페이징타입에는 numbers, simple_numbers, full_numbers 등 있음
	"ordering": true,		//정렬
	"searching": false,		//검색
	"info": false,		//정보 표시
	language: lang_kor		//한글
	} );
	
	
} );