package com.main.glory.model.dyeingSlip.request;

import com.main.glory.model.dyeingSlip.DyeingSlipMast;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlipFormatData extends DyeingSlipMast{

    Double totalWt;
    Double totalMeter;
    String qualityId;
    Long qualityEntryId;
    String partyShadeNo;
    Long batchCount;
    String colorTone;
    String colorName;
    String jetName;
    String partyName;


    public SlipFormatData(DyeingSlipMast dyeingSlipMastExist) {
        super(dyeingSlipMastExist);
    }

}
