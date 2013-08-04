<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${course.name}</title>
</head>
<body>
<h1>${course.name}</h1>
<p>
<img src="${course.pics[0]}"/>
<p>
简介
${course.description}
<p>
制作方法
${course.steps[0]}<br>
${course.steps[1]}<br>
${course.steps[2]}<br>
</body>
</html>