--LDR
pin_led = 4

dim_max = 600
adc_val = 0

gpio.mode(pin_led,gpio.OUTPUT)
gpio.mode(pin_led+1,gpio.OUTPUT)
gpio.mode(pin_led+2,gpio.OUTPUT)
gpio.mode(pin_led+3,gpio.OUTPUT)
gpio.mode(pin_led+4,gpio.OUTPUT)
gpio.mode(pin_led+5,gpio.OUTPUT)

--LDR

tmr.alarm(0,1500,1, function()


                gpio.write(pin_led,gpio.LOW)
                gpio.write(pin_led+1,gpio.LOW)
                gpio.write(pin_led+2,gpio.LOW)
                gpio.write(pin_led+3,gpio.LOW)
                gpio.write(pin_led+4,gpio.LOW)
                gpio.write(pin_led+5,gpio.LOW)
                
                adc_val = (adc.read(0)*100)/dim_max
                
                if( adc_val > 20)then
                    gpio.write(pin_led,gpio.HIGH)
                end
                
                if( adc_val > 60)then
                    gpio.write(pin_led+1,gpio.HIGH)
                end
                
                if( adc_val > 80)then
                    gpio.write(pin_led+2,gpio.HIGH)
                end
               
                print(adc_val)
                --tmr.stop(0)           
end)

