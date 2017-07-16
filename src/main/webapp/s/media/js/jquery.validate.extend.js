jQuery.validator.addMethod("mobile",function(value,element){
    var tel =/^(130|131|132|133|134|135|136|137|138|139|150|151|152|153|155|156|157|158|159|180|181|182|183|184|185|187|188|189|176|170|177)\d{8}$/;
    return tel.test(value)||this.optional(element);

},'请输入正确的手机号码！');