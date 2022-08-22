<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

	<head>
		<style>
			table, th, td {
			  border: 1px solid black;
			  border-collapse: collapse;
			}
		</style>
        <!-- 제이쿼리(jQuery) CDN -->
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
        <!-- handlebars CDN -->
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js"></script>
	</head>
	<body>
		<script id="pokemon-template" type="text/x-handlebars-template">
			<table>
				<colgroup>
					<col width="20%">
					<col width="40%">
					<col width="40%">
				</colgroup>
				<thead>
					<th>번호</th>
					<th>이름</th>
					<th>타입</th>
				</thead>
				<tbody>
					{{#﻿pokemons}}
					<tr>
						<td>{{seq}}</td>
						<td>{{name}}</td>
						<td>{{type}}</td>
					</tr>
					{{/﻿pokemons}}
				</tbody>
			</table>
		</script>
		<script type="text/javascript">
			// 핸들바 템플릿을 가져옵니다.
			var source = $("#pokemon-template").html();
			// 핸들바 템플릿을 pre컴파일합니다.
			var template = Handlebars.compile(source);
			// 핸들바 템플릿에 바인딩할 데이터입니다.
			var data = {
				﻿pokemons:[
				    { seq: 1, name:"피카츄", type:"전기" },
				    { seq: 2, name:"라이츄", type:"전기" },
				    { seq: 3, name:"파이리", type:"불" },
				    { seq: 4, name:"꼬부기", type:"물" }
				]
			};
			// 핸들바 템플릿에 데이터를 바인딩해서 HTML을 생성합니다.
			var itemList = template(data);
			// 생성된 HTML을 append처리합니다.
			$('body').append(itemList);
		</script>
	</body>
