package com.epam.finalProject.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CartTag extends SimpleTagSupport {
    double total;

    public void setValue(double total) {
        this.total = total;
    }

    @Override
    public void doTag() throws JspException, IOException {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);
        String numberAsString = df.format(total);
        getJspContext().getOut().print(numberAsString);
    }
}
