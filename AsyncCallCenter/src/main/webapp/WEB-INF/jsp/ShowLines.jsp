<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Call Center Simulation</title>
<style>
.username.ng-valid {
	background-color: lightgreen;
}

.username.ng-dirty.ng-invalid-required {
	background-color: red;
}

.username.ng-dirty.ng-invalid-minlength {
	background-color: yellow;
}

.email.ng-valid {
	background-color: lightgreen;
}

.email.ng-dirty.ng-invalid-required {
	background-color: red;
}

.email.ng-dirty.ng-invalid-email {
	background-color: yellow;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {
		$('#button').on('click', function() {
			var text = $('#uname');
			text.val(text.val() + ' after clicking');
		});
	});
</script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="<c:url value='css/app.css' />" rel="stylesheet"></link>
</head>
<body ng-app="myApp" class="ng-cloak">
	<div class="generic-container" ng-controller="CallController as ctrl">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">Almundo - Call Center
					${CallController.calls}</span>
			</div>
			<div class="formcontainer">
				<form ng-submit="ctrl.submit()" name="myForm"
					class="form-horizontal">
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="file">Nro.
								Llamadas</label>
							<div class="col-md-7">
								<input type="number" ng-model="ctrl.ncalls" name="calls"
									class="username form-control input-sm"
									placeholder="Enter your name" required id="nrocalls"
									ng-minlength="1" ng-pattern="/^[0-9]*$/" ng-min="1"
									ng-max="1000" /> (Numeros entre 1 - 1000)
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.calls.$error.required">El numero de llamadas es requerido</span> <span ng-show="myForm.calls.$invalid">
										Numero o formato invalido </span>
								</div>
								<div ng-repeat="u in ctrl.workers">
									<span ng-bind="u"></span>
								</div>
							</div>
						</div>
					</div>
					Llamadas en espera :<span ng-bind="ctrl.waiting"></span>

					<div class="row">
						<div class="form-actions floatRight">
							<input type="submit" value="Generar Llamadas"
								class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
							<button type="button" ng-click="ctrl.reset()"
								class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset
								Form</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<span class="lead">Lines Monitor </span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th width="10%">ID Linea</th>
							<th width="15%">Status</th>
							<th width="10%">ID Llamada</th>
							<th width="15%">Tiempo</th>
							<th>Empleado</th>


						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="u in ctrl.lines">
							<td><span ng-bind="u.id"></span></td>
							<td><span ng-bind="u.status"></span></td>
							<td><span ng-bind="u.call"></span></td>
							<td><span ng-bind="u.time"></span></td>
							<td><span ng-bind="u.worker"></span></td>

						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script>
		
	</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
	<script src="<c:url value='js/app.js' />"></script>
	<script src="<c:url value='js/service/call_service.js' />"></script>
	<script src="<c:url value='js/controller/call_controller.js' />"></script>
</body>
</html>