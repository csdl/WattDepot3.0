<!DOCTYPE html>
<html>
<head>
<title>WattDepot Administration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="/webroot/dist/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="/webroot/dist/css/bootstrap-theme.min.css">
<link rel="stylesheet/less" type="text/css" href="/webroot/dist/css/style.less">
<script src="/webroot/dist/js/less-1.3.0.min.js"></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://code.jquery.com/jquery.js"></script>
<script src="/webroot/dist/js/bootstrap.min.js"></script>
<script src="/webroot/dist/js/wattdepot-admin.js"></script>
<script> 
$(function(){
  $("#modal-dialogs").load("/webroot/dist/dialogs.html"); 
});
</script> 

</head>
<body>
  <div id="modal-dialogs"></div>
  <div class="container">
  <!-- Nav tabs -->
    <ul class="nav nav-tabs">
        <#if groupId == "admin">
        <li><a id="users_tab_link" href="#users" data-toggle="tab">Users</a></li>
        </#if>
        <li><a id="depositories_tab_link" href="#depositories" data-toggle="tab">Depositories</a></li>
        <li><a id="locations_tab_link" href="#locations" data-toggle="tab">Locations</a></li>
        <li><a id="sensors_tab_link" href="#sensors" data-toggle="tab">Sensors</a></li>
        <li><a id="sensorgroups_tab_link" href="#sensorgroups" data-toggle="tab">Sensor Groups</a></li>
        <li><a id="sensormodels_tab_link" href="#sensormodels" data-toggle="tab">Sensor Models</a></li>
        <li><a id="sensorprocesses_tab_link" href="#sensorprocesses" data-toggle="tab">Sensor Processes</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div class="tab-pane <#if groupId == "admin">active</#if>" id="users">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Users</h3></th></tr>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list users as u>
                        <tr><td>${u.firstName!}</td><td>${u.lastName!}</td><td>${u.id}</td><td>${u.email!}</td>
                            <td>
                                <#if ! u.admin><span class="glyphicon glyphicon-pencil" onclick="edit_user_dialog(event, '${u.id}');"></span></#if>
                            </td>
                            <td>
                                <#if ! u.admin><span class="glyphicon glyphicon-remove" onclick="delete_user_dialog(event, '${u.id}');"></span></#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addUserModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add User</button>
            </div>       
            <div class="well">
                <table class="table">
                    <thead>
                        <tr>
                            <th colspan="2"><h3>Groups</h3></th>                            
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list groups as g>
                        <tr><td>${g.id}</td><td><#list g.users as u>${u.id} </#list></td>
                            <td>
                                <#if g.id != "admin"><span class="glyphicon glyphicon-pencil" onclick="edit_group_dialog(event, '${g.id}');"></span></#if>
                            </td>
                            <td>
                                <#if g.id != "admin"><span class="glyphicon glyphicon-remove" onclick="delete_group_dialog(event, '${g.id}');"></span></#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addUserGroupModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add User Group</button>
            </div>        
        </div>
        <div class="tab-pane <#if groupId != "admin">active</#if>" id="depositories">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Depositories</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Measurement Type</th>
                            <#if groupId == "admin">
                            <th>Owner</th>
                            </#if>
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list depositories as d>
                        <tr><td>${d.name}</td><td>${d.measurementType}</td><#if groupId == "admin"><td>${d.owner.id}</td></#if>
                            <td>
                                <span class="glyphicon glyphicon-pencil" onclick="edit_depository_dialog(event, '${d.name}');"></span>
                            </td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_depository_dialog(event, '${d.name}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addDepositoryModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Depository</a>
            </div>       
        </div>
        <div class="tab-pane" id="locations">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Locations</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Altitude</th>
                            <th>Description</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list locations as l>
                        <tr><td>${l.id}</td><td>${l.latitude}</td><td>${l.longitude}</td><td>${l.altitude}</td><td>${l.description}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_location_dialog(event, '${l.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addLocationModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Location</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensors">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensors</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>URI</th>
                            <th>Location</th>
                            <th>Model</th>
                            <th>Properties</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensors as s>
                        <tr><td>${s.id}</td><td>${s.uri}</td><td>${s.location}</td><td>${s.model}</td><td>${s.properties}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_sensor_dialog(event, '${l.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#sensorModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensorgroups">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensor Groups</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Sensors</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensorgroups as g>
                        <tr><td>${g.id}</td><td>${g.sensors}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_group_dialog(event, '${g.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#sensorGroupModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Group</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensormodels">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensor Models</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Protocol</th>
                            <th>Type</th>
                            <th>Version</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensormodels as m>
                        <tr><td>${m.id}</td><td>${m.protocol}</td><td>${m.type}</td><td>${m.version}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_model_dialog(event, '${m.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#modelModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Model</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensorprocesses">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensor Processes</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Sensor</th>
                            <th>Polling Interval</th>
                            <th>Depository</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensorprocesses as p>
                        <tr><td>${p.id}</td><td>${p.sensor.id}</td><td>${p.pollingInterval}</td><td>${p.depositoryId}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_process_dialog(event, '${p.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#processModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Process</button>
            </div>       
        </div>
    </div>  
    
    
  <!-- Add User Group -->
  <div class="modal fade" id="addUserGroupModal" tabindex="-1" role="dialog" aria-labelledby="addUserGroupModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add User Group</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Group Name</label>
                        <input type="text" name="usergroup_name" class="form-inline"><p></p>
                </div>
                <div class="row">
                            <label class="col-md-3 col-md-offset-1">Users</label>
                            <select name="groupusers" multiple="multiple">
                            <#list users as u>
                                <option value="${u.id}">${u.id}</option>
                            </#list>
                            </select>
                </div>
                <div class="clearfix"></div>
                </form>
            </div>                
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewUserGroup();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    


</div>
<script>
$(document).ready(function () {
    var selected_tab = getCookie("selected-tab");
    if (selected_tab != null) {
        $('#' + selected_tab + '_tab_link').tab('show');
    }
});

var GROUPID = "${groupId}";
var USERS = {};
<#list users as u>
USERS["${u.id}"] = {"id": "${u.id}", "firstName" : "${u.firstName!"none"}", "lastName" : "${u.lastName!"none"}", "email" : "${u.email!"none"}", "password" : "${u.password!"none"}", "admin" : <#if u.admin>true<#else>false</#if>, "properties" : [<#assign k = u.properties?size><#list u.properties as p>{"key":"${p.key}", "value":"${p.value}"}<#if k != 1>,</#if><#assign k = k -1></#list>]};
</#list>
var USERGROUPS = {};
<#list groups as g>
USERGROUPS["${g.id}"] = {"id": "${g.id}", "users": [
<#assign j = g.users?size>
<#list g.users as u>
{"id": "${u.id}", "firstName" : "${u.firstName!"none"}", "lastName" : "${u.lastName!"none"}", "email" : "${u.email!"none"}", "password" : "${u.password!"none"}", "admin" : <#if u.admin>true<#else>false</#if>, "properties" : [<#assign k = u.properties?size><#list u.properties as p>{"key":"${p.key}", "value":"${p.value}"}<#if k != 1>,</#if><#assign k = k -1></#list>]}<#if j != 1>,</#if><#assign j = j - 1>
</#list>
]};
</#list>
var DEPOSITORIES = {};
<#list depositories as d>
DEPOSITORIES["${d.name}"] = {"name": "${d.name}", "measurementType": "${d.measurementType}", "owner": "${d.owner.id}"};
</#list>
var LOCATIONS = {};
<#list locations as l>
LOCATIONS["${l.id}"] = {"id": "${l.id}", "latitude": ${l.latitude}, "longitude": ${l.longitude}, "altitude": ${l.altitude}, "description": "${l.description}", "owner": "${l.owner.id}"};
</#list>
</script>
</body>
</html>