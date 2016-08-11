# Import file "design" (sizes and positions are scaled 1:4)
sketch = Framer.Importer.load("imported/design@4x")
#Utils.globalLayers sketch

memoCount = 0;

ViewController = require 'ViewController'
Views = new ViewController
    initialView: sketch.Main
    #initialView: sketch.NewRecord
	animationOptions:
		time: .5
		curve: "ease-in-out"

sketch.fabRecord.states.add
	init:
		#visible: false
		midX: sketch.fab.midX
		midY: sketch.fab.midY
	clicked:
		#visible: true
		x: sketch.fabRecord.x
		y: sketch.fabRecord.y
		
sketch.fabMemo.states.add
	init:
		#visible: false
		midX: sketch.fab.midX
		midY: sketch.fab.midY
	clicked:
		#visible: true
		x: sketch.fabMemo.x
		y: sketch.fabMemo.y
		
sketch.noMemos.visible = false
sketch.item1.visible = true
sketch.item2.visible = true
sketch.fabMask.visible = false
sketch.fabRecord.states.switchInstant("init")
sketch.fabMemo.states.switchInstant("init")

sketch.fab.onClick ->
	sketch.fabMemo.states.switch("clicked", curve: "spring(400, 30, 0)")
	sketch.fabRecord.states.switch("clicked", curve: "spring(400, 30, 0)")
	sketch.fabPlus.rotation = 45
	sketch.fabMask.visible = true
	
sketch.fabMemo.onClick ->
	Views.fadeIn(sketch.NewMemo)
	sketch.fabMemo.states.switch("init", curve: "spring(400, 30, 0)")
	sketch.fabRecord.states.switch("init", curve: "spring(400, 30, 0)")
	sketch.fabPlus.rotation = 0
	sketch.fabMask.visible = false

sketch.fabRecord.onClick ->
	Views.fadeIn(sketch.NewRecord)
	sketch.fabMemo.states.switch("init", curve: "spring(400, 30, 0)")
	sketch.fabRecord.states.switch("init", curve: "spring(400, 30, 0)")
	sketch.fabPlus.rotation = 0
	sketch.fabMask.visible = false

# New Memo
sketch.back.onClick ->
	Views.back()
	
sketch.done.onClick ->
	Views.back()
	sketch.newMemoText.visible = false
	sketch.Hint_text_insertion_mark_copy.visible = true
	sketch.done.visible = false
	
sketch.new_memo.onClick ->
	sketch.newMemoText.visible = true
	sketch.Hint_text_insertion_mark_copy.visible = false
	sketch.done.visible = true

# New Record
sketch.backRecord.onClick ->
	Views.back()
	
animationRecording = new Animation
    layer: sketch.circle
    properties:
        rotation: 360
    curve: "linear"
    repeat: 100
    
varNewRecordState = "init"
sketch.button.onClick ->
	if varNewRecordState is "init"
		sketch.recordingText.visible = true
		sketch.circle.visible = true
		sketch.record.visible = true
		sketch.start.visible = false
		sketch.doneRecord.visible = true
		varNewRecordState = "recording"
		animationRecording.start()
	else if varNewRecordState is "recording"
		addNewRecord()

sketch.doneRecord.onClick ->
	Views.back()
	sketch.recordingText.visible = false
	sketch.circle.visible = false
	sketch.record.visible = false
	sketch.start.visible = true
	sketch.doneRecord.visible = false
		
varNewRecord = 0
addNewRecord = ->
	varNewRecord++
	if varNewRecord is 1
		sketch.item1Record.visible = true		
	else if varNewRecord is 2
		sketch.item2Record.visible = true	
	
	
	
	
	

# Hello world!