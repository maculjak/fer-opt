package hr.fer.zemris.optjava.generic.ga;

import hr.fer.zemris.optjava.art.GrayScaleImage;

public class Evaluator implements IGAEvaluator<int[]> {
    private GrayScaleImage template;
    private GrayScaleImage im;

    public Evaluator(GrayScaleImage template) {
        super();
        this.template = template;
    }

    public GrayScaleImage draw(GASolution<int[]> p, GrayScaleImage im) {
        if(im==null) {
            im = new GrayScaleImage(template.getWidth(), template.getHeight());
        }
        int[] pdata = p.getData();
        byte bgcol = (byte)pdata[0];
        im.clear(bgcol);
        int n = (pdata.length-1)/5;
        int index = 1;
        for(int i = 0; i < n; i++) {
            im.rectangle(
                    pdata[index], pdata[index+1], pdata[index+2], pdata[index+3],
                    (byte)pdata[index+4]
            );
            index+=5;
        }
        return im;
    }

    @Override
    public void evaluate(GASolution<int[]> p) {
// Ovo nije višedretveno sigurno!
        if(im == null) {
            im = new GrayScaleImage(template.getWidth(), template.getHeight());
        }
        draw(p, im);
        byte[] data = im.getData();
        byte[] tdata = template.getData();
        int w = im.getWidth();
        int h = im.getHeight();

        double error = 0;
        int index2=0;
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                error += Math.abs(((int)data[index2]&0xFF)-((int)tdata[index2]&0xFF));
                index2++;
            }
        }
        p.fitness = -error;
    }
}
