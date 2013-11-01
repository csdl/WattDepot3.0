function putNewUser() {
    var id = $("input[name='user_id']").val();
    var first = $("input[name='user_firstname']").val();
    var last = $("input[name='user_lastname']").val();
    var pass = $("input[name='user_password']").val();
    var email = $("input[name='user_email']").val();
    var admin = $("input[name='user_admin']").is(':checked');
    var usr = {
        "id" : id,
        "firstName" : first,
        "lastName" : last,
        "email" : email,
        "password" : pass,
        "admin" : "false",
        "properties" : []
    };
    if (admin) {
        usr['admin'] = "true";
    }
    console.log(users);
    $.ajax({
        url : '/wattdepot/admin/user/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(usr),
        success : function() {
            location.reload();
        },
    });
};

function delete_user_dialog(event, id) {
    var modalElement = $('#deleteUserModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_user_id').html(id);
    modalElement.modal('show');
};

function deleteUser() {
    var id = $('#del_user_id').html();
    $.ajax({
        url : '/wattdepot/admin/user/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

function getKnownUser(id) {
    return users[id];
}

function putNewUserGroup() {
    var id = $("input[name='usergroup_name']").val();
    var selected_ids = $("select[name='groupusers']").val() || [];
    console.log(selected_ids);
    var selected_users = new Array();
    for (var i = 0; i < selected_ids.length; i++) {
        selected_users.push(getKnownUser(selected_ids[i]));
    }
    console.log(selected_users);
    var grp = {
        "id" : id,
        "users" : selected_users
    };
    console.log(JSON.stringify(grp))
    $.ajax({
        url : '/wattdepot/admin/usergroup/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(grp),
        success : function() {
            location.reload();
        },
    });
};

function delete_usergroup_dialog(event, id) {
    var modalElement = $('#deleteUserGroupModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_usergroup_id').html(id);
    modalElement.modal('show');
};

function deleteUseGroupr() {
    var id = $('#del_usergroup_id').html();
    $.ajax({
        url : '/wattdepot/admin/usergroup/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};

function putNewDepository() {
    var id = $("input[name='depositoryname']").val();
    var type = $("input[name='depositorytype']").val();
    var depo = {
        "name" : id,
        "measurementType" : type
    };
    $.ajax({
        url : '/wattdepot/' + GROUPID + '/depository/temp',
        type : 'PUT',
        contentType : 'application/json',
        data : JSON.stringify(depo),
        success : function() {
            location.reload();
        },
    });
};

function delete_depository_dialog(event, id) {
    var modalElement = $('#deleteUserModal');

    modalElement.modal({
        backdrop : true,
        keyboard : true,
        show : false
    });
    modalElement.find('#del_depository_id').html(id);
    modalElement.modal('show');
};

function deleteDepository() {
    var id = $('#del_depository_id').html();
    $.ajax({
        url : '/wattdepot/' + GROUP + '/depository/' + id,
        type : 'DELETE',
        contentType : 'application/json',
        success : function() {
            location.reload();
        },
    });
};
