pin_led_a = 1




gpio.mode(pin_led_a,gpio.OUTPUT)

gpio.write(pin_led_a,gpio.LOW)

wifi.setmode(wifi.STATION)
--wifi.sta.config("DIGISOL","digi@12345")
wifi.sta.config("SmartStreet","paraskijaiho")
--wifi.sta.config("Connectify-baahubali","paraskijaiho")

wifi.sta.setip({ip="192.168.225.77",netmask="255.255.255.0",gateway="192.168.225.1"})
print("Connected to IP "..wifi.sta.getip())


dofile("ldr.lua")
--global adc_val

srv=net.createServer(net.TCP)
srv:listen(80,function(conn)
    conn:on("receive", function(client,request)
        local buf = "";
        local _, _, method, path, vars = string.find(request, "([A-Z]+) (.+)?(.+) HTTP");
        if(method == nil)then
            _, _, method, path = string.find(request, "([A-Z]+) (.+) HTTP");
        end
        local _GET = {}
        if (vars ~= nil)then
            for k, v in string.gmatch(vars, "(%w+)=(%w+)&*") do
                _GET[k] = v
            end
        end
        buf = buf.."<body bgcolor=\"beige\"><h1><font color = \"blue\">Light Pattern Control System</font></h1></body>";
        buf = buf.."<h2><font color = \"DARKSLATEGRAY\">University of Jammu</font></h2>";
        
        buf = buf.."<font color = \"ROSYBROWN\">â€¢ Connected to ESP on "..wifi.sta.getip();
        --buf = buf.."<img src=\"https://www.dropbox.com/pri/get/Public/B1058384900.jpg?_subject_uid=76159750&w=AAAMcSl2noXDleBg7EB9VzXlxwUqicZ8juNhtIxaY5EhVA\">";
        
        buf = buf.."</font><br><p><a href=\"?pin=INBLT\"><button>ESP LED</button></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"?pin=LED\"><button>LED</button></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"?pin=BULB\"><button>BULB</button></a></p>";
        buf = buf.."<p><a href=\"?pin=ALLON\"><button>LIGHTS ON</button></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"?pin=ALLOFF\"><button>LIGHTS OFF</button></a></p>";
        buf = buf.."<p><a href=\"?pin=TEST\"><button>GO</button></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"?pin=ALLOFF\"><button>LIGHTS OFF</button></a></p>";
        
        local _on,_off = "",""
        
        if(_GET.lampno == "1")then
            if(_GET.pin == "ON")then
                gpio.write(pin_led_a,gpio.HIGH)
            else
                gpio.write(pin_led_a,gpio.LOW)
            end
  
        elseif(_GET.lampno == "2")then
                if(_GET.pin == "ON")then
                    gpio.write(pin_led_a+1,gpio.HIGH)
                else
                    gpio.write(pin_led_a+1,gpio.LOW)
                end
              
              
        elseif(_GET.lampno == "3")then
                if(_GET.pin == "ON")then
                    gpio.write(pin_led_a+2,gpio.HIGH)
                else
                    gpio.write(pin_led_a+2,gpio.LOW)
                end

         elseif(_GET.lampno == "4")then
                if(_GET.pin == "ON")then
                    gpio.write(pin_led_a+3,gpio.HIGH)
                else
                    gpio.write(pin_led_a+3,gpio.LOW)
                end

         elseif(_GET.lampno == "5")then
                if(_GET.pin == "ON")then
                    gpio.write(pin_led_a+4,gpio.HIGH)
                else
                    gpio.write(pin_led_a+4,gpio.LOW)
                end

         elseif(_GET.lampno == "6")then
                if(_GET.pin == "ON")then
                    gpio.write(pin_led_a+5,gpio.HIGH)
                else
                    gpio.write(pin_led_a+5,gpio.LOW)
                end

         elseif(_GET.lampno == "0")then
                if(_GET.pin == "tmr_on")then
                    tmr.start(0)
                else
                    tmr.stop(0)
                end
                          
        end

        
        

        client:send(adc_val);
        client:close();
        collectgarbage();
    end)
end)

