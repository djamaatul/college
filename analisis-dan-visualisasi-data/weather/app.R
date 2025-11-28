if(!require("shiny")) packages.install("shiny");
if(!require("readxl")) packages.install("readxl");
if(!require("tibble")) packages.install("tibble");
if(!require("ggplot2")) packages.install("ggplot2");

library(shiny);
library(readxl);
library(tibble);
library(ggplot2);

raw <- readxl::read_excel("./weather.xlsx", skip = 1);
colnames <- names(raw);
jenis <- c(
           "Scatter Plot" = "scatter", 
           "Line Plot" = "line",
           "Bar Plot" = "bar",
           "Table" = "table"
          );

ui <- fluidPage(
  titlePanel("Wheater"),
  tableOutput("identity"),
  sidebarLayout(
    sidebarPanel(
      selectInput("jenis", label = "Jenis", choices = jenis),
      conditionalPanel("input.jenis != 'table'",
       selectInput("x", label = "Variable X", choices = colnames),
       selectInput("y", label = "Variable Y", choices = colnames)
      )
    ),
    mainPanel(
      conditionalPanel("input.jenis != 'table'",
                       plotlyOutput("plot", height = "520px")
      ),
      conditionalPanel("input.jenis == 'table'",
                       DTOutput("table")
      )
    )
  )
)

server <- function (input, output, session){
  data <- reactive(raw);
  output$identity <- renderTable(data.frame(Nama = "D. Jamaatul Anbiya", NIM = "051700434"))
  
  output$plot <- renderPlotly({
    jenis <- input$jenis;
    req(jenis != "table");
    
    df <- data();

    xname <- input$x;
    yname <- input$y;
    
    df <- df[order(df[[xname]]), ]
    
    x <- type.convert(df[[xname]], as.is = TRUE);
    y <- type.convert(df[[yname]], as.is = TRUE);
    
    if(jenis == "scatter"){
      p <- plot_ly(df, x = x, y = y, type = "scatter", mode = "markers")
      return(p);
    }
    
    if(jenis == "line"){
      p <- p <- plot_ly(df, x = x, y = y, type = "scatter", mode = "line")
      return(p);
    }
    
    if (jenis == "bar") {
      p <- ggplot(df, aes(x = x, y = y)) +
        geom_bar(stat = "identity")
      return(p);
    }
  });
  
  output$table <- renderDT({
    req(input$jenis == "table")
    datatable(raw);
  })
}

app <- shinyApp(ui, server)

runApp(app, port = 3000)