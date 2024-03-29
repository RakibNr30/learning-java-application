<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<html>
<head>
    <title>${ title }</title>
    <%@ include file="/views/partials/front/style.jsp" %>
</head>
<body>

<div class="container">
    <%@ include file="/views/partials/front/header.jsp" %>

    <div class="card">
        <div class="card-header">
            <h5 class="card-title float-start">Add User</h5>
            <a href="/user" class="btn btn-primary btn-sm float-end">User List</a>
        </div>
        <div class="card-body">
            <div class="container">
                <%-- notifier --%>
                <%@ include file="/views/partials/front/notifier.jsp" %>

                <form class="row g-3" action="/user/store" method="POST">
                    <div class="col-md-6">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" id="name" name="name" placeholder="Enter name" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label for="dob" class="form-label">Date of Birth</label>
                        <input type="date" id="dob" name="dob" placeholder="Enter date of birth" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label for="email" class="form-label">E-mail</label>
                        <input type="email" id="email" name="email" placeholder="Enter email" class="form-control" required>
                    </div>
                    <div class="col-md-6">
                        <label for="address" class="form-label">Address</label>
                        <input type="text" id="address" name="address" placeholder="Enter address" class="form-control" required>
                    </div>
                    <div class="col-md-12">
                        <label for="selectedSkills" class="form-label">Skills</label>
                        <select id="selectedSkills" name="selectedSkills" class="form-select" multiple aria-label="multiple select">
                            <c:forEach items="${skills}" var="skill">
                                <option value="${skill.id}">${skill.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-12">
                        <div class="float-end">
                            <button type="reset" class="btn btn-danger">Reset</button>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>

</body>
</html>