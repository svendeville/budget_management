/*
 * @2016 Sylvain Vendeville.
 * This file is part of Budget Managment.
 *
 * Budget Management is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Budget Management is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MesComptes. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Sylvain
 */
@SpringBootApplication(scanBasePackages = {"com.snv"})
@EnableSwagger2
public class BudgetManagementApplication {

    public static void main(final String[] args) throws Exception {
        new SpringApplicationBuilder(BudgetManagementApplication.class).web(true).run(args);
    }
}
