package server;

public class PowUpFactory {
    public PowUp makePowUp(int newPowUpType) {
        PowUp newPowUp = null;

        if(1 == newPowUpType)
            return new JumpPowUp();

        else if(2 == newPowUpType)
            return  new DashPowUp();

        else if(3 == newPowUpType)
            return  new SlowPowUp();

        else if(4 == newPowUpType)
            return  new TeleportPowUp();
        else if(5 == newPowUpType)
            return  new PastPowUp();

        return  newPowUp;
    }


}
