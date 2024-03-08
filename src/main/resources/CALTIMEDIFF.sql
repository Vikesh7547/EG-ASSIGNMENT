CREATE OR REPLACE PROCEDURE CALTIMEDIFF(p_start_time IN TIMESTAMP,
                                        p_end_time   IN TIMESTAMP,
                                        p_duration   IN NUMBER,
                                        p_calender_id IN VARCHAR2,
                                        p_flag       out varchar2,
                                        p_out_msg    OUT VARCHAR2) IS
  v_available_start     TIMESTAMP;
  v_available_end       TIMESTAMP;
  w_diff                INTERVAL DAY TO SECOND;
  w_start_time          TIMESTAMP;
  w_end_time            TIMESTAMP;
  v_flag                BOOLEAN := TRUE;
  W_COUNT               NUMBER;
  w_start_time_new      TIMESTAMP;
  w_end_time_new        TIMESTAMP;
  w_available_startslot TIMESTAMP;
  w_available_endslot   TIMESTAMP;
  wslot_time            timestamp;
  w_full_time           timestamp;

BEGIN
  p_flag := 'S';
  -- Initialize the start and end times with the input values
  w_start_time := p_start_time;
  w_end_time   := p_end_time;
  BEGIN
  
    -- Attempt to find an existing appointment within the specified time range
  
    SELECT min(a.startdate), max(a.enddate)
      INTO v_available_start, v_available_end
      FROM appointments a
    
     WHERE ((a.startdate BETWEEN w_start_time AND w_end_time) or
           (a.enddate BETWEEN w_start_time AND w_end_time)
           
           ) and a.calendar_id=p_calender_id;
  
    SELECT count(*)
      into W_COUNT
      FROM appointments a
    
     WHERE ((a.startdate BETWEEN w_start_time AND w_end_time) or
           (a.enddate BETWEEN w_start_time AND w_end_time)
           
           ) and a.calendar_id=p_calender_id;
    if (W_COUNT > 1) then
      DBMS_OUTPUT.put_line('inside count');
    else
    
      SELECT a.startdate, a.enddate
        INTO v_available_start, v_available_end
        FROM appointments a
      
       WHERE ((a.startdate BETWEEN w_start_time AND w_end_time) or
             (a.enddate BETWEEN w_start_time AND w_end_time)
             
             ) and a.calendar_id=p_calender_id
         and rownum = 1;
    
    end if;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      v_flag := false;
      p_flag := 'S';
      -- Handle the case when no appointment is found
      wslot_time := w_start_time + INTERVAL '1' MINUTE * p_duration;
      p_out_msg  := '  slot are avaible for given period .' || w_start_time || 'to' ||
                    wslot_time;
    
  END;

  -- Check if the end time needs adjustment (it is greater than the available end time)

  if w_start_time < v_available_start THEN
    -- Check if the start time needs adjustment (it is less than the available start time)
    w_diff           := v_available_start - w_start_time;
    w_start_time_new := w_start_time + w_diff;
    wslot_time       := w_start_time + INTERVAL '1' MINUTE * p_duration;
    if (wslot_time > w_start_time_new) then
      p_out_msg := ' The slot is fully booked.';
    
    else
    
      p_out_msg := 'Available slot: ' || w_start_time || 'TO' || ' ' ||
                   wslot_time || ' ' || 'Do you wish to Cotinue';
    end if;
  
    p_flag := 'F';
    -- p_date:=w_start_time||'|'||w_start_time;
  elsIF w_end_time > v_available_end THEN
    w_diff         := w_end_time - v_available_end;
    w_end_time_new := w_end_time - w_diff;
    wslot_time     := w_end_time_new + INTERVAL '1' MINUTE * p_duration;
    if (wslot_time > w_end_time) then
    
      p_out_msg := ' The slot is fully booked.';
    else
    
      p_out_msg := 'Available slot: ' || w_end_time_new || ' ' || 'to' || ' ' ||
                   wslot_time || ' ' || 'Do you wish to Cotinue';
    
      p_flag := 'F';
    end if;
  
  ELSIF v_flag THEN
    p_flag    := 'F';
    p_out_msg := 'The slot is fully booked.';
  
  END IF;

  -- Display the result
END;
