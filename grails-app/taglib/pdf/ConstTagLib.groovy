package pdf

class ConstTagLib {
    /*
     * name: name attribute of input element.
     * class_name: input element's css style class.
     */
    def pdf_photo_size_lists = { attributes ->

    }
    /*
     * name: name attribute of select element.
     * class_name: select element's css style class.
     */
    def pdf_document_size_lists = { attributes ->
        if(attributes.getAt('class_name') != null){
            out << "<select name=\"${attributes.name}\" class=\"${attributes.class_name}\">";
        }else{
            out << "<select name=\"${attributes.name}\">";
        }
        DocumentSize.getSizeList().each { key,value ->
            out << "<option value=\"${key}\"";
            if(value.isDefault()){
                out << ' selected="selected"';
            }
            out << '>';
            if(value.getMessageID() != null){
                out << message(code: value.getMessageID());
            }else{
                out << key;
            }
            out << '</option>';
        }
        out << '</select>';
    }
}
