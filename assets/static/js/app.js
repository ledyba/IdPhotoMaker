/**
 *　顔の各パーツの場所を指定する。
 * mode: 'row' or 'col' 線のモードを指定
 * photo_url: 写真のURLを指定
 *
 */

function PhotoManager(mode,photo_url,width,height,guide_url) {
    //モードの決定
    mode = mode.toLowerCase();
    if(mode == 'col'){
        this.Mode = 1;
    }else if(mode == 'row'){
        this.Mode = 2;
    }else{
        throw "invalid mode: {"+mode+"}";
    }
    this.PhotoURL = photo_url;
    this.GuideWidth=120;
    this.GuideHeight=160;
    this.GuideURL = guide_url;
    this.Width = width;
    this.Height = height;
    /**
     *
     * append_id: appendする先のエレメントのID。
     */
    this.appendTo = function(photo_id,slector_id,wrapper_id,append_id){
        this.PhotoID = photo_id;
        this.SelectorID = slector_id;
        this.SubSelectorID = 'sub_'+slector_id;
        this.WrapperID = wrapper_id;
        this.AppendID = append_id;
        var photo_node = this.makePhotoNode(this.PhotoURL, photo_id);
        var selector_node = this.makeSelectorNode(slector_id);
        var subselector_node = this.makeSubSelectorNode(this.SubSelectorID);
        var guide_node = this.makeGuideNode(this.GuideURL,this.GuideWidth,this.GuideHeight);
        var wrapper_node = this.makeWrapperNode(wrapper_id, photo_node, guide_node, selector_node, subselector_node);
        document.getElementById(append_id).innerHTML='';
        document.getElementById(append_id).appendChild(wrapper_node);
    };
    this.FuncMainSlider = function(v){
        $(this.InputID).value = v;
        this.SubSlider.setValue(v);
    }.bind(this);
    this.FuncSubSlider = function(v){
        $(this.InputID).value = v;
        this.MainSlider.setValue(v);
    }.bind(this);
    this.setSlider = function(input_id,default_pos){
        // vertical slider control
        this.InputID = input_id;
        var opts;
        var opts_sub;
        if(this.Mode == 1){
            opts = {
                axis: 'vertical',
                sliderValue: default_pos,
                onSlide: this.FuncMainSlider//,
//                onChange: this.FuncMainSlider
            };
            opts_sub = {
                axis: 'vertical',
                sliderValue: default_pos,
                onSlide: this.FuncSubSlider//,
//                onChange: this.FuncSubSlider
            };
        }else{
            opts = {
                axis: 'hotizon',
                sliderValue: default_pos,
                onSlide: this.FuncMainSlider//,
//                onChange: this.FuncMainSlider
            };
            opts_sub = {
                axis: 'hotizon',
                sliderValue: default_pos,
                onSlide: this.FuncSubSlider//,
//                onChange: this.FuncSubSlider
            };
        }
        this.MainSlider = new Control.Slider(this.SelectorID,this.WrapperID, opts);
        this.SubSlider = new Control.Slider(this.SubSelectorID,this.WrapperID, opts_sub);
        var effect_opts = {
            from:0.5, // 開始時透明度
            to:1.0, // 終了時透明度
            // delay:0, // 開始までの秒数
            fps:30, // フレームレート
            duration: 5, // アニメーションする時間(秒)
            beforeStartInternal: function(effect) {
            },
            afterFinishInternal: function(effect) {
            }
        };
        new Effect.Pulsate(this.SelectorID, effect_opts);
        new Effect.Pulsate(this.SubSelectorID, effect_opts);

        document.getElementById(this.InputID).value = default_pos;
    }
    this.makePhotoNode = function(photo_url,photo_id){
        var photo_node = document.createElement('img');
        photo_node.className = 'face_image';
        photo_node.src = photo_url;
        photo_node.id = photo_id;
        return photo_node;
    };
    /**
     * mode: MODE_ROW or MODE_COL
     * photo_node: 写真のエレメント
     */
    this.makeSelectorNode = function(selector_id){
        var selector_node = document.createElement('div');
        selector_node.className="pos_selector";
        if(this.Mode == 1){
            selector_node.style.height = '1px';
            selector_node.style.width = (this.Width+4)+'px';
            selector_node.style.left = '-1px';
        }else{
            selector_node.style.height = (this.Height+4)+'px';
            selector_node.style.width = '1px';
            selector_node.style.top = '-1px';
        }
        selector_node.id = selector_id;
        return selector_node;
    }
    this.makeSubSelectorNode = function(sub_selector_id){
        var sub_selector_node = document.createElement('div');
        sub_selector_node.className="pos_selector";
        if(this.Mode == 1){
            sub_selector_node.style.height = '13px';
            sub_selector_node.style.width = '13px';
            sub_selector_node.style.left = '-15px';
        }else{
            sub_selector_node.style.height = '13px';
            sub_selector_node.style.width = '13px';
            sub_selector_node.style.top = '-15px';
        }
        sub_selector_node.id = sub_selector_id;
        return sub_selector_node;
    }
    this.makeWrapperNode = function(wrapper_id,photo_node,guide_node,selector_node,subselector_node){
        var wrapper_node = document.createElement('div');
        wrapper_node.id = wrapper_id;
        wrapper_node.className = 'pos_wrapper';
        wrapper_node.style.height = this.Height+'px';
        wrapper_node.style.width = this.Width+'px';
        var wrapper_meter_node = document.createElement('div');
        wrapper_meter_node.className = 'pos_meter';
        if(this.Mode == 1){
            wrapper_meter_node.style.height = (this.Height-2)+'px';
            wrapper_meter_node.style.width = '13px';
            wrapper_meter_node.style.left = '-15px';
        }else{
            wrapper_meter_node.style.height = '13px';
            wrapper_meter_node.style.width = (this.Width-2)+'px';
            wrapper_meter_node.style.top = '-15px';
        }
        wrapper_node.appendChild(guide_node);
        wrapper_node.appendChild(wrapper_meter_node);
        wrapper_node.appendChild(selector_node);
        wrapper_node.appendChild(subselector_node);
        wrapper_node.appendChild(photo_node);
        return wrapper_node;
    }
    this.makeGuideNode = function(guide_url,guide_width,guide_height){
        var guide_wrapper_node = document.createElement('div');
        guide_wrapper_node.className = 'guide_wrapepr';
        guide_wrapper_node.style.height = guide_height+'px';
        guide_wrapper_node.style.width = guide_width+'px';
        guide_wrapper_node.style.left = this.Width+2+'px';
        var guide_node = document.createElement('img');
        guide_node.src = guide_url;
        guide_node.style.width = guide_width+'px';
        guide_node.style.height = guide_height+'px';
        guide_node.style.padding = '0px';
        guide_node.style.margin = '0px';
        guide_wrapper_node.appendChild(guide_node);
        return guide_wrapper_node;
    }
}

function PhotoSizeListManager(name_base,wrapper_id,width_message,height_message,btn_message,photo_cnt_message){
    this.WrapperNodeID = wrapper_id;
    this.WrapperNode = document.getElementById(wrapper_id);
    this.InputNode = document.createElement('div');
    this.InputCount = 0;
    this.WidthMessage = width_message;
    this.HeightMessage = height_message;
    this.BtnMessage = btn_message;
    this.NameBase = name_base;
    this.CountMessage = photo_cnt_message;

    this.BtnClickEvent = function(){
        var photo_size_wrapper_node = document.createElement('div');
        photo_size_wrapper_node.style.marginBottom='5px';

        var photo_size_width_message_node  = document.createElement('span');
        photo_size_width_message_node.innerHTML=this.WidthMessage+':';
        var photo_size_height_message_node  = document.createElement('span');
        photo_size_height_message_node.innerHTML=this.HeightMessage+':';

        var photo_size_width_cm_message_node  = document.createElement('span');
        photo_size_width_cm_message_node.innerHTML='cm';
        var photo_size_height_cm_message_node  = document.createElement('span');
        photo_size_height_cm_message_node.innerHTML='cm';

        var photo_size_count_message_node  = document.createElement('span');
        photo_size_count_message_node.innerHTML=this.CountMessage;

        var input_width_node = document.createElement('input');
        input_width_node.className='photo_size';
        input_width_node.type='text';
        input_width_node.name=name_base+';'+this.InputCount+';width';

        var input_height_node = document.createElement('input');
        input_height_node.className='photo_size';
        input_height_node.type='text';
        input_height_node.name=name_base+';'+this.InputCount+';height';

        var input_count_node = document.createElement('input');
        input_count_node.className='photo_size';
        input_count_node.type='text';
        input_count_node.name=name_base+';'+this.InputCount+';count';

        var space_node = document.createElement('span');
        space_node.innerHTML='&nbsp;';

        photo_size_wrapper_node.appendChild(photo_size_height_message_node);
        photo_size_wrapper_node.appendChild(input_height_node);
        photo_size_wrapper_node.appendChild(photo_size_height_cm_message_node);
        photo_size_wrapper_node.appendChild(space_node.cloneNode(true));
        photo_size_wrapper_node.appendChild(photo_size_width_message_node);
        photo_size_wrapper_node.appendChild(input_width_node);
        photo_size_wrapper_node.appendChild(photo_size_width_cm_message_node);
        photo_size_wrapper_node.appendChild(space_node.cloneNode(true));
        photo_size_wrapper_node.appendChild(input_count_node);
        photo_size_wrapper_node.appendChild(photo_size_count_message_node);

        this.InputCount++;
        this.InputNode.appendChild(photo_size_wrapper_node);
    }
    this.makeButtonNode = function(){
        var btn_wrapper_node = document.createElement('div');
        var btn_node = document.createElement('input');
        btn_node.type='button';
        btn_node.name=this.WrapperNodeID+'_btn';
        btn_node.value=this.BtnMessage;
        btn_node.onclick=this.BtnClickEvent.bind(this);

        btn_wrapper_node.appendChild(btn_node);
        this.WrapperNode.appendChild(btn_wrapper_node);
    }
    this.WrapperNode.innerHTML='';
    this.WrapperNode.appendChild(this.InputNode);
    this.makeButtonNode();
    this.BtnClickEvent();
}
