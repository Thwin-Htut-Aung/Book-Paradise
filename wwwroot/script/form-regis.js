/**
 * 
 */
$(document).ready(function () {

    $.validator.setDefaults({
        errorClass: "invalid-feedback",
        highlight: function (element) {
            $(element)
                .closest('.form-control')
                .addClass('is-invalid')
        },
        unhighlight: function (element) {
            $(element)
                .closest('.form-control')
                .removeClass('is-invalid')
        }
    })

    $.validator.addMethod("customEmail", function (value, element) {
        return this.optional(element) || /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(value);
    }, "Please enter valid email address!");
    
    $.validator.addMethod('filesize', function (value, element, param) {
    return this.optional(element) || (element.files[0].size <= param * 1000000)
	}, 'File size must be less than 3 MB');

    $("#regForm").validate({

        rules: {
            fullName: {
                required: true,
            },
            address: {
                required: true
            },
            phoneNumber: {
                required: true,
            },
           	country: {
	          required: true
	        },
	        city: {
	          required: true
	        },
	        issue: {
				required: true
			},
			organizationName: {
				required: true
			},
            email: {
                required: true,
                customEmail: true
            },
            password: {
                required: true,
                minlength: 6,
                maxlength: 25,
                normalizer: function (value) {
                    return $.trim(value);
                }
            },
            pass2: {
                required: true,
                equalTo: '#password'
            },
            file: {
				required: true,
				accept: "application/pdf",
				filesize: 3
			},
        },
        messages: {
            fullName: {
                required: 'Full name is empty'
            },
            address: {
                required: 'Address is empty'
            },
            phoneNumber: {
                required: 'Phone number is required'
            },
           	country: {
	          required: 'Country is empty'
	        },
	        city: {
	          required: 'City is empty'
	        },
	        issue: {
				required: 'Select one of above'
			},
			organizationName: {
				required: 'Organization name is empty'
			},
            email: {
                required: 'Email is required'
            },
            password: {
                required: 'Password is required'
            },
            pass2: {
                required: 'Confirm password is required',
                equalTo: 'Password did not match, Please enter the same password'
            },
            file: {
				required: 'File is required',
				accept: 'Only accept pdf file'
			}
        }
    })
})
